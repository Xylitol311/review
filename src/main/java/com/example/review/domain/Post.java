package com.example.review.domain;

import com.example.review.type.PostCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue
    private Long postId;
    private String title;
    @Enumerated(EnumType.STRING)
    private PostCategory category;
    private String text;
    
    @CreatedDate
    private LocalDateTime postCreatedDate;
    @LastModifiedDate
    private LocalDateTime postUpdatedDate;
    private LocalDateTime postDeletedDate;
    @ColumnDefault("0")
    private Long postCommentCount;
    @ManyToOne
    private Member member;
    
    
}
