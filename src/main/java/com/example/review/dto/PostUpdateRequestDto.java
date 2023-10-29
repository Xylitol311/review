package com.example.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateRequestDto {
    private String title;
    private String category;
    private String text;
    private Long memberId;
}
