package com.example.review.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
}
