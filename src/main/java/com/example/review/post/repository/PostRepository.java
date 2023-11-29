package com.example.review.post.repository;

import com.example.review.post.domain.Post;
import com.example.review.post.type.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAllByTitle(Pageable pageable, String title);
    Page<Post> findAllByCategory(Pageable pageable, PostCategory postCategory);
    Page<Post> findAllByPostCreatedDate(Pageable pageable, LocalDate postCreatedDate);
    
    Page<Post> findAllByMember_Nickname(Pageable pageable, String nickname);
    Page<Post> findAllByMember_MemberId(Pageable pageable, Long memberId);
    
    Long countByMember_MemberId(Long memberId);
}
