package com.example.review.member.controller;

import com.example.review.member.dto.MemberSignupRequest;
import com.example.review.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;
    
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid MemberSignupRequest memberSignupRequest) {
        memberService.signup(memberSignupRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
