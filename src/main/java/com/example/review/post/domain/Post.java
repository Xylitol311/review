package com.example.review.post.domain;

import com.example.review.member.domain.Member;
import com.example.review.post.dto.PostCreateRequestDto;
import com.example.review.post.dto.PostUpdateRequestDto;
import com.example.review.post.type.PostCategory;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
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
@Builder(builderMethodName = "PostBuilder")
@DynamicUpdate
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    public void update(PostUpdateRequestDto postUpdateRequestDto) {
        this.title = postUpdateRequestDto.getTitle();
        this.category = postUpdateRequestDto.getCategory();
        this.text = postUpdateRequestDto.getText();
    }
    
    public static PostBuilder builder(PostCreateRequestDto postCreateRequestDto, Member member) {
        return PostBuilder()
                .title(postCreateRequestDto.getTitle())
                .category(postCreateRequestDto.getCategory())
                .text(postCreateRequestDto.getText())
                .member(member)
                .postCommentCount(0L);
    }
}
