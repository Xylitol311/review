package com.example.review.security;

import com.example.review.member.domain.Member;
import com.example.review.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private MemberRepository memberRepository;
    
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findByLoginId(username).orElseThrow(
                () -> new UsernameNotFoundException("Not Found " + username));
        
        return new UserDetailsImpl(member);
    }
}
