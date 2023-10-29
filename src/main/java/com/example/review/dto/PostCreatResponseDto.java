package com.example.review.dto;

import com.example.review.type.PostCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreatResponseDto {
    private String title;
    private PostCategory category;
    private String text;
    private Long memberId;
}
