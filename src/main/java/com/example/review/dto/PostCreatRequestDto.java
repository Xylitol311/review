package com.example.review.dto;

import com.example.review.domain.Member;
import com.example.review.type.PostCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreatRequestDto {
    private String title;
    private PostCategory category;
    private String text;
    private Long memberId;
}
