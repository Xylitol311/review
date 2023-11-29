package com.example.review.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;
    @Override
    public String toString() {
        return errorCode + " : " + errorCode.getMessage();
    }
}