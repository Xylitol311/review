package com.example.review.member.service;

import com.example.review.exception.ErrorCode;
import com.example.review.exception.LoginIdExistsException;
import com.example.review.exception.NicknameDuplicatedException;
import com.example.review.member.domain.Member;
import com.example.review.member.domain.Role;
import com.example.review.member.dto.MemberSignupRequest;
import com.example.review.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public void signup(MemberSignupRequest request) {
        // login id 중복 체크
        if (memberRepository.findByLoginId(request.getLoginId()).isPresent()) {
            throw new LoginIdExistsException(ErrorCode.LOGIN_ID_EXISTS, ErrorCode.LOGIN_ID_EXISTS.getMessage());
        }
        
        // 닉네임 중복 체크
        if (memberRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new NicknameDuplicatedException(ErrorCode.NICKNAME_DUPLICATED, ErrorCode.NICKNAME_DUPLICATED.getMessage());
        }
        
        Member member = Member.builder()
                .loginId(request.getLoginId())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .role(Role.USER)
                .build();
        memberRepository.save(member);
    }
}
