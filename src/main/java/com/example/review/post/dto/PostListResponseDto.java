package com.example.review.post.dto;

import com.example.review.post.domain.Post;
import com.example.review.post.type.PostCategory;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "PostListResponseDtoBuilder")
public class PostListResponseDto {
    private Long postId;
    private String title;
    private PostCategory category;
    private LocalDate postCreatedDate;
    private Long postCommentCount;
    private String nickname;
    
    public static PostListResponseDtoBuilder builder(Post post) {
        return PostListResponseDtoBuilder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .category(post.getCategory())
                .postCreatedDate(post.getPostCreatedDate())
                .postCommentCount(post.getPostCommentCount())
                .nickname(post.getMember().getNickname());
    }
}
