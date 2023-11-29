package com.example.review.comment.dto;

import com.example.review.comment.domain.Comment;
import com.example.review.post.domain.Post;
import com.example.review.post.dto.PostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "CommentResponseDtoBuilder")
public class CommentResponseDto {
    private Long id;
    private String username;
    private String comment;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    
    public static CommentResponseDtoBuilder builder(Comment comment) {
        return CommentResponseDtoBuilder()
                .id(comment.getCommentId())
                .username(comment.getMember().getLoginId())
                .comment(comment.getCommentText())
                .createdAt(comment.getCommentCreatedDate())
                .modifiedAt(comment.getCommentUpdatedDate())
                .nickname(comment.getMember().getNickname());
    }
}
