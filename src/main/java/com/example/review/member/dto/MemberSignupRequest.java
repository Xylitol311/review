package com.example.review.member.dto;

import com.example.review.post.type.PostCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSignupRequest {
    private String loginId;
    private String password;
    private String nickname;
}
