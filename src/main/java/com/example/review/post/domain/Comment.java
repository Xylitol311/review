package com.example.review.post.domain;

import com.example.review.member.domain.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue
    private Long commentId;
    private String commentText;
    @CreatedDate
    private LocalDate commentCreatedDate;
    @LastModifiedDate
    private LocalDate commentUpdatedDate;
    private LocalDate commentDeletedDate;
    @ManyToOne
    private Member member;
    @ManyToOne
    private Post post;
}
