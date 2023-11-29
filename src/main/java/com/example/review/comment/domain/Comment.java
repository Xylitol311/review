package com.example.review.comment.domain;

import com.example.review.comment.dto.CommentRequestDto;
import com.example.review.comment.dto.CommentResponseDto;
import com.example.review.member.domain.Member;
import com.example.review.post.domain.Post;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "CommentBuilder")
public class Comment {
    @Id
    @GeneratedValue
    private Long commentId;
    private String commentText;
    @CreatedDate
    private LocalDateTime commentCreatedDate;
    @LastModifiedDate
    private LocalDateTime commentUpdatedDate;
    private LocalDateTime commentDeletedDate;
    @ManyToOne
    private Member member;
    @ManyToOne
    private Post post;
    
    public static CommentBuilder builder(CommentRequestDto commentRequestDto, Post post, Member member) {
        return CommentBuilder()
                .commentText(commentRequestDto.getComment())
                .member(member)
                .post(post);
    }
    
    public void update(CommentRequestDto commentRequestDto) {
        this.commentText = commentRequestDto.getComment();
    }
}
