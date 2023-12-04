package com.example.review.comment.controller;

import com.example.review.comment.dto.CommentRequestDto;
import com.example.review.comment.pagination.CommentPageInfo;
import com.example.review.comment.pagination.CommentPageResponse;
import com.example.review.comment.service.CommentService;
import com.example.review.config.security.UserDetailsImpl;
import com.example.review.post.pagination.PostPageInfo;
import com.example.review.post.pagination.PostPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;
    @PostMapping("/{postId}")
    public ResponseEntity<?> createComment(@PathVariable Long postId, @RequestBody @Valid CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.createComment(postId, commentRequestDto, userDetails.getUser());
        return ResponseEntity.ok("success");
    }
    
    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody @Valid CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentRequestDto, userDetails.getUser()));
    }
    
    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(postId, commentId, commentRequestDto, userDetails.getUser());
        return ResponseEntity.ok("success");
    }
    
    @GetMapping("/{postId}")
    public ResponseEntity<CommentPageResponse> readComments(
            @PathVariable Long postId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo
    ) {
        CommentPageInfo commentPageInfo = CommentPageInfo.builder(pageNo).build();
        CommentPageResponse commentPageResponse = commentService.findCommentsByPostId(commentPageInfo, postId);
        
        return ResponseEntity.ok(commentPageResponse);
    }
}
