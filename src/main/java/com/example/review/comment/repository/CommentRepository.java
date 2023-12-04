package com.example.review.comment.repository;

import com.example.review.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentIdAndMember_MemberId(Long commentId, Long memberId);
    
    Page<Comment> findAllByPost_PostId(Pageable pageable, Long postId);
}
