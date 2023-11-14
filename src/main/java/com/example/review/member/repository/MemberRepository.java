package com.example.review.member.repository;

import com.example.review.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    
    Optional<Member> findByNickname(String nickname);
}
