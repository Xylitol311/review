package com.example.review.dto;

import com.example.review.type.PostCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListResponseDto {
    private Long postId;
    private String title;
    private PostCategory category;
    private LocalDateTime postCreatedDate;
    private Long postCommentCount;
    private Long memberId;
    
}
