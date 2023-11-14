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
    @ExceptionHandler(UserNotFoundException.class)
    // 요청한 postId가 존재하지 않음
    public ResponseEntity<?> userNotFoundExceptionHandler(UserNotFoundException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(e.toString());
    }
    @ExceptionHandler(LoginIdExistsException.class)
    // 요청한 postId가 존재하지 않음
    public ResponseEntity<?> loginIdExistsExceptionHandler(LoginIdExistsException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(e.toString());
    }
    @ExceptionHandler(NicknameDuplicatedException.class)
    // 요청한 postId가 존재하지 않음
    public ResponseEntity<?> nicknameDuplicatedExceptionHandler(NicknameDuplicatedException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(e.toString());
    }
}
