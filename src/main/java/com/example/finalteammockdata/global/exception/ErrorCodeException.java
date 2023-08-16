package com.example.finalteammockdata.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorCodeException extends RuntimeException {
    HttpStatus status;

    private ErrorCodeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    private ErrorCodeException(String message, Integer status) {
        super(message);
        this.status = HttpStatus.valueOf(status);
    }

    public static ErrorCodeException make(Integer status, String message) {
        return new ErrorCodeException(message, status);
    }
    public static ErrorCodeException make(HttpStatus status, String message) {
        return new ErrorCodeException(message, status);
    }
}
