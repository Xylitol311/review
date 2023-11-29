package com.example.review.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberSignupRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String checkPassword;
    private boolean admin = false;
    private String adminToken = "";
    @NotBlank
    private String nickname;
}
