package com.example.review.domain;

import com.example.review.type.PostCategory;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Post {
    @Id
    @GeneratedValue
    private Long postId;
    private String title;
    @Enumerated(EnumType.STRING)
    private PostCategory category;
    private String text;
    
    @CreatedDate
    private LocalDate postCreatedDate;
    @LastModifiedDate
    private LocalDate postUpdatedDate;
    private LocalDate postDeletedDate;
    @ColumnDefault("0") // h2 db 생성 시에 기본 컬럼값을 0으로 지정. null값이 아닌 아무 값도 입력되지 않으면 0으로 입력된다.
    private Long postCommentCount;
    @ManyToOne
    private Member member;
    
    
}
