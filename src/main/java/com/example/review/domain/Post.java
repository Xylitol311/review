package com.example.review.domain;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long postId;
    private String title;
    private String category;
    private String text;
    
    @CreatedDate
    private LocalDateTime postCreatedDate;
    @LastModifiedDate
    private LocalDateTime postUpdatedDate;
    private LocalDateTime postDeletedDate;
    @ColumnDefault("0")
    private String postCommentCount;
    @ManyToOne
    private Member member;
    
    
}
