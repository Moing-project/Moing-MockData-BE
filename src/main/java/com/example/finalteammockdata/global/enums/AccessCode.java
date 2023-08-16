package com.example.finalteammockdata.global.enums;

import org.springframework.http.HttpStatus;

public enum AccessCode {
    EMAIL_CORRECT_ERROR("A03", HttpStatus.NOT_ACCEPTABLE),
    ACCESS_TOKEN_ERROR("A08",HttpStatus.BAD_REQUEST),
    WORKSPACE_CREATE_ALLOW("A10",HttpStatus.CREATED),

    NULL_ERROR(null, null);

    AccessCode(String errorName, HttpStatus httpStatus) {
        this.errorName = "ACCESS_" + errorName;
        this.status = httpStatus;
    }
    AccessCode(String errorName, int httpStatus) {
        this.errorName = "ACCESS_" + errorName;
        this.status = HttpStatus.valueOf(httpStatus);
    }

    public String getName() {
        return this.errorName;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    private final String errorName;
    private final HttpStatus status;
}
