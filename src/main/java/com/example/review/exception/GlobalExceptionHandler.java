package com.example.review.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PostNotFoundException.class)
    // 요청한 postId가 존재하지 않음
    public ResponseEntity<?> postNotFoundExceptionHandler(PostNotFoundException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(e.toString());
    }
    
    @ExceptionHandler(MemberNotFoundException.class)
    // 요청한 memberId에 해당하는 member가 존재하지 않음
    public ResponseEntity<?> memberNotFoundExceptionHandler(MemberNotFoundException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(e.toString());
    }
}
