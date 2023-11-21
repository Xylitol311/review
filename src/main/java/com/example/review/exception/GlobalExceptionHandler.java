package com.example.review.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    // 요청한 postId가 존재하지 않음
    public ResponseEntity<?> customExceptionHandler(CustomException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(e.toString());
    }
    
}
