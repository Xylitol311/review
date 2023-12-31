package com.example.review.post.dto;

import com.example.review.comment.pagination.CommentPageResponse;
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
    private String text;
    private LocalDate postCreatedDate;
    private LocalDate postUpdatedDate;
    private Long postCommentCount;
    private String nickname;
    private CommentPageResponse commentList;
    
    public static PostResponseDtoBuilder builder(Post post, CommentPageResponse commentList) {
        return PostResponseDtoBuilder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .category(post.getCategory())
                .text(post.getText())
                .postCreatedDate(post.getPostCreatedDate())
                .postUpdatedDate(post.getPostUpdatedDate())
                .postCommentCount(post.getPostCommentCount())
                .nickname(post.getMember().getNickname())
                .commentList(commentList);
    }
}
