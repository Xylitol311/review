package com.example.review.repository;

import com.example.review.domain.Post;
import com.example.review.type.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTitle(String title);
    List<Post> findAllByCategory(PostCategory postCategory);
    List<Post> findAllByPostCreatedDate(LocalDate localDate);
    
    List<Post> findAllByMember_Nickname(String nickname);
    List<Post> findAllByMember_MemberId(Long memberId);
}
