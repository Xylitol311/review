package com.example.review.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {
    @Id
    @GeneratedValue
    Long memberId;
    private String loginId;
    private String nickname;
    private String password;
}
