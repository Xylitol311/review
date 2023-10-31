package com.example.review.domain;

import com.example.review.type.PostCategory;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //의미 없는 객체의 무분별한 생성을 막을 수 있다
//JPA Proxy 때문에 어쩔 수 없이 매개변수가 없는 기본 생성자를 만들었는데, 이걸 어디선가 잘못사용하면 불완전한 객체가 만들어질 수 있다.
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
    private LocalDateTime postCreatedDate;
    @LastModifiedDate
    private LocalDateTime postUpdatedDate;
    private LocalDateTime postDeletedDate;
    @ColumnDefault("0") // h2 db 생성 시에 기본 컬럼값을 0으로 지정. null값이 아닌 아무 값도 입력되지 않으면 0으로 입력된다.
    private Long postCommentCount;
    @ManyToOne
    private Member member;
    
    
}
