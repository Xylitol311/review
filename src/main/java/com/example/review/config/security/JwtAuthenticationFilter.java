package com.example.review.config.security;

import com.example.review.member.domain.UserRoleEnum;
import com.example.review.member.dto.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/user/login"); // 로그인 요청 URI
    }
    
    // 로그인 시, username 과 password 를 바탕으로 UsernamePasswordAuthenticationToken 을 발급
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);     // JSON 형식의 데이터를 변환(username(LoginID), password, 변환할 타입)
            
            return getAuthenticationManager().authenticate(  // 생성된 Authentication객체를 가지고 AuthenticationManager에게 위임
                    new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword(),null)    // 로그인 요청에 입력된 username, password 정보로 토큰을 생성
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    
    // 로그인 인증 성공 시 실행
    // 여기서 로그인을 요청한 사용자에게 JWT토큰이 생성됨 (인증 완료)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername(); // username
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getUserRoleEnum();  // role
        
        String token = jwtUtil.createToken(username, role); // 로그인 성공 시, username, role을 담은 토큰 생성
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token); // 헤더에 해당 토큰 추가
    }
    
    // 로그인 실패 시
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
}
