package com.example.review.member.service;

import com.example.review.comment.domain.Comment;
import com.example.review.comment.repository.CommentRepository;
import com.example.review.config.security.JwtUtil;
import com.example.review.exception.CustomException;
import com.example.review.exception.ErrorCode;
import com.example.review.member.domain.Member;
import com.example.review.member.domain.UserRoleEnum;
import com.example.review.member.dto.MemberLoginRequest;
import com.example.review.member.dto.MemberSignupRequest;
import com.example.review.member.repository.MemberRepository;
import com.example.review.post.domain.Post;
import com.example.review.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.example.review.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public void signup(MemberSignupRequest requestDto) {
        final String ADMIN_TOKEN = "fawoefjidkzhviuerl";
        
        String loginId = requestDto.getUsername();  // requestDto 에서 사용자 이름을 조회
        String password = passwordEncoder.encode(requestDto.getPassword()); // requestDto 에서 비밀번호를 조회
        
        // login id 중복 체크
        Optional<Member> checkUsername = memberRepository.findByLoginId(loginId);        // 조회한 이름이 DB 에 있는지 확인
        if (checkUsername.isPresent()) {
            throw new CustomException(ErrorCode.LOGIN_ID_EXISTS);
        }
        
        // 닉네임 중복 체크
        if (memberRepository.findByNickname(requestDto.getNickname()).isPresent()) {
            throw new CustomException(ErrorCode.NICKNAME_DUPLICATED);
        }
        
        // 비밀번호 일치 확인
        if (!requestDto.getPassword().equals(requestDto.getCheckPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }
        
        // 사용자 ROLE 확인 (admin = true 일 경우 아래 코드 수행)
        UserRoleEnum userRoleEnum = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new CustomException(NOT_MATCH_ADMIN);
            }
            userRoleEnum = UserRoleEnum.ADMIN;
        }
        
        
        Member member = Member.builder()
                .loginId(loginId)
                .password(password)
                .nickname(requestDto.getNickname())
                .userRoleEnum(userRoleEnum)
                .build();
        
        memberRepository.save(member);
    }
    
    public void login(MemberLoginRequest memberLoginRequest, HttpServletResponse response) {
        String loginId = memberLoginRequest.getUsername();
        String password = memberLoginRequest.getPassword();
        
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        
        // 비밀번호 일치 여부 확인
        if (!member.getPassword().equals(password)) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
            //수정 필요
            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, JwtUtil.createToken(member.getLoginId()));
        }
    }
    
    public Post findByPostIdAndMember(Long postId, Member member) {
        Post post;
        
        // ADMIN
        if (member.getUserRoleEnum().equals(UserRoleEnum.ADMIN)) {
            post = postRepository.findById(postId).orElseThrow(
                    () -> new CustomException(POST_NOT_FOUND)
            );
            // USER findByPostIdAndMember
        } else {
            post = postRepository.findByPostIdAndMember_MemberId(postId, member.getMemberId()).orElseThrow(
                    () -> new CustomException(NOT_FOUND_POST_OR_AUTHORIZATION)
            );
        }
        
        return post;
    }
    
    public Comment findByCommentIdAndMember(Long commentId, Member member) {
        Comment comment;
        
        // ADMIN
        if (member.getUserRoleEnum().equals(UserRoleEnum.ADMIN)) {
            comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new CustomException(COMMENT_NOT_FOUND)
            );
            // USER
        } else {
            comment = commentRepository.findByCommentIdAndMember_MemberId(commentId, member.getMemberId()).orElseThrow(
                    () -> new CustomException(NOT_FOUND_COMMENT_OR_AUTHORIZATION)
            );
        }
        
        return comment;
    }
}
