package com.example.review.post.dto;

import com.example.review.post.domain.Post;
import com.example.review.post.type.PostCategory;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "PostResponseDtoBuilder")
public class PostResponseDto {
    private Long postId;
    private String title;
    private PostCategory category;
    private LocalDate postCreatedDate;
    private LocalDate postUpdatedDate;
    private Long postCommentCount;
    private String nickname;
    // 추후 댓글도 추가
    
    public static PostResponseDtoBuilder builder(Post post) {
        return PostResponseDtoBuilder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .category(post.getCategory())
                .postCreatedDate(post.getPostCreatedDate())
                .postUpdatedDate(post.getPostUpdatedDate())
                .postCommentCount(post.getPostCommentCount())
                .nickname(post.getMember().getNickname());
    }
}
