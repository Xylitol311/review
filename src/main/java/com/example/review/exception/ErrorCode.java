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
    NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "Nickname is duplicated."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "Passwords do not match."),
    NOT_MATCH_ADMIN(HttpStatus.BAD_REQUEST, "Administrator password does not match."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Comment not found."),
    AUTHORIZATION(HttpStatus.BAD_REQUEST, "You do not have permission."),
    NOT_FOUND_POST_OR_AUTHORIZATION(HttpStatus.BAD_REQUEST, "Post not found or You do not have permission."),
    NOT_FOUND_COMMENT_OR_AUTHORIZATION(HttpStatus.BAD_REQUEST, "Comment not found or You do not have permission.");
    
    private HttpStatus status;
    private String message;
}
