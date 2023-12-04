package com.example.review.config.security;

import com.example.review.member.domain.Member;
import com.example.review.member.domain.UserRoleEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final Member member;        // 인증 완료된 사용자 추가
    
    public Member getUser() {         // 인증 완료된 Member(User)를 가져오는 Getter
        return member;
    }
    
    @Override
    public String getPassword() {
        return member.getPassword();
    }
    
    @Override
    public String getUsername() {
        return member.getLoginId();
    }
    
    // 사용자의 권한을 GrantedAuthority 로 추상화 및 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    // authorities를 통해 권한 제어 가능. GrantedAuthority : 현재 사용자가 가지고 있는 권한
        UserRoleEnum userRoleEnum = member.getUserRoleEnum();
        String authority = userRoleEnum.getAuthority();
        
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);
        
        return authorities;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
