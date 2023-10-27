package com.example.review.dto;

import com.example.review.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreatRequestDto {
    private String title;
    private String category;
    private String text;
    private Member member;
}
