package com.example.review.comment.controller;

import com.example.review.comment.dto.CommentRequestDto;
import com.example.review.comment.dto.CommentResponseDto;
import com.example.review.comment.pagination.CommentPageResponse;
import com.example.review.comment.service.CommentService;
import com.example.review.config.MsgResponseDto;
import com.example.review.config.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Slf4j
public class CommentController {
  private final CommentService commentService;
  
  @PostMapping("/{postId}")
  public ResponseEntity<CommentResponseDto> createComment(
          @PathVariable Long postId, @RequestBody @Valid CommentRequestDto commentRequestDto,
          @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    
    log.info(userDetails.getUser().getLoginId());
    CommentResponseDto commentResponseDto = commentService.createComment(postId, commentRequestDto, userDetails.getUser());
    return ResponseEntity.ok(commentResponseDto);
  }
  
  @PutMapping("/{postId}/{commentId}")
  public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody @Valid CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    CommentResponseDto commentResponseDto = commentService.updateComment(postId, commentId, commentRequestDto, userDetails.getUser());
    return ResponseEntity.ok(commentResponseDto);
  }
  
  @DeleteMapping("/{postId}/{commentId}")
  public ResponseEntity<MsgResponseDto> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    MsgResponseDto msgResponseDto = commentService.deleteComment(postId, commentId, userDetails.getUser());
    return ResponseEntity.ok(msgResponseDto);
  }
  
  @GetMapping("/{postId}")
  public ResponseEntity<CommentPageResponse> readComments(
          @PathVariable Long postId,
          @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo
  ) {
    CommentPageResponse commentPageResponse = commentService.readComments(pageNo, postId);
    
    return ResponseEntity.ok(commentPageResponse);
  }
}
