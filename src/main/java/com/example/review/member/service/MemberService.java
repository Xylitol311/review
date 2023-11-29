package com.example.review.member.service;

import com.example.review.exception.*;
import com.example.review.member.domain.Member;
import com.example.review.member.domain.Role;
import com.example.review.member.dto.MemberLoginRequest;
import com.example.review.member.dto.MemberSignupRequest;
import com.example.review.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

import static com.example.review.exception.ErrorCode.NOT_MATCH_ADMIN;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public void signup(MemberSignupRequest request) {
        final String ADMIN_TOKEN = "fawoefjidkzhviuerl";
        // login id 중복 체크
        if (memberRepository.findByLoginId(request.getUsername()).isPresent()) {
            throw new LoginIdExistsException(ErrorCode.LOGIN_ID_EXISTS, ErrorCode.LOGIN_ID_EXISTS.getMessage());
        }
        
        // 닉네임 중복 체크
        if (memberRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new NicknameDuplicatedException(ErrorCode.NICKNAME_DUPLICATED, ErrorCode.NICKNAME_DUPLICATED.getMessage());
        }
        
        // 비밀번호 일치 확인
        if (!request.getPassword().equals(request.getCheckPassword())) {
            throw new PasswordMismatchException(ErrorCode.PASSWORD_MISMATCH, ErrorCode.PASSWORD_MISMATCH.getMessage());
        }
        
        // 사용자 ROLE 확인 (admin = true 일 경우 아래 코드 수행)
        Role role = Role.USER;
        if (request.isAdmin()) {
            if (!ADMIN_TOKEN.equals(request.getAdminToken())) {
                throw new NotMatchAdmin(NOT_MATCH_ADMIN,NOT_MATCH_ADMIN.getMessage());
            }
            role = Role.ADMIN;
        }
        
        
        Member member = Member.builder()
                .loginId(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .role(Role.USER)
                .build();
        
        memberRepository.save(member);
    }
    
    public void login(MemberLoginRequest memberLoginRequest, HttpServletResponse response) {
        String username = memberLoginRequest.getUsername();
        String password = memberLoginRequest.getPassword();
        
        Member member = memberRepository.findByLoginId(username).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
        );
        
        // 비밀번호 일치 여부 확인
        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        
        //수정 필요
//        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, JwtUtil.createToken(member.getLoginId()));
    }
}
