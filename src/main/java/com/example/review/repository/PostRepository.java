package com.example.review.repository;

import com.example.review.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PostRepository extends JpaRepository<Post, Long> {
}
