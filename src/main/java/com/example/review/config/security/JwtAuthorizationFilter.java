package com.example.review.config.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = jwtUtil.getJwtFromHeader(request); // header 에서 JWT 가져오기
        
        if (StringUtils.hasText(tokenValue)) {
            if (!jwtUtil.validateToken(tokenValue)) { // JWT 토큰 검증
                log.error("Token Error");
                return;
            }
            
            Claims info = jwtUtil.getUserInfoFromToken(tokenValue); // JWT 토큰에서 사용자 정보 가져오기
            
            try {
                setAuthentication(info.getSubject()); // 인증 처리
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }
    
    // 인증 처리
    public void setAuthentication(String username) {
        // SecurityContextHolder : authentication을 담고 있는 Holder
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        // 인증 객체 생성
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);
        
        // SecurityContextHolder에 인증된 객체를 저장하면, 해당 인증 객체를 전역적으로 사용할 수 있음
        SecurityContextHolder.setContext(context);
    }
    
    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // 인증된 사용자의 정보와 권한을 담은 UsernamePaswordAuthenticationToken 생성
    }
}
