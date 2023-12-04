package com.example.review.member.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    Long memberId;
    private String loginId;
    private String nickname;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRoleEnum;
    @CreatedDate
    private LocalDate signUpDate;
}
