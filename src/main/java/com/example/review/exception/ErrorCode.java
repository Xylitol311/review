package com.example.review.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not found."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found."),
    LOGIN_ID_EXISTS(HttpStatus.BAD_REQUEST, "Login ID is exists."),
    NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "Nickname is duplicated.");
    
    private HttpStatus status;
    private String message;
}
