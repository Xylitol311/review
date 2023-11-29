package com.example.review.comment.controller;

import com.example.review.comment.dto.CommentRequestDto;
import com.example.review.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private CommentService commentService;
    @PostMapping("/{postId}")
    public ResponseEntity<?> createComment(@PathVariable Long postId, @RequestBody @Valid CommentRequestDto commentRequestDto, HttpServletRequest request) {
        commentService.createComment(postId, commentRequestDto, request);
        return ResponseEntity.ok("success");
    }
    
    @PutMapping("/{boardId}/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody @Valid CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return ResponseEntity.ok(commentService.updateComment(boardId, commentId, commentRequestDto, request));
    }
    
    @DeleteMapping("/{boardId}/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return ResponseEntity.ok("success");
    }
}
