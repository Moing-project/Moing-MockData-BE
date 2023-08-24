package com.example.finalteammockdata.global.enums;

import org.springframework.http.HttpStatus;

public enum DeniedCode {

    DO_VALID_ERROR("A02",HttpStatus.BAD_REQUEST),
    EMAIL_CORRECT_ERROR("A03",HttpStatus.CONFLICT),
    NICKNAME_CORRECT_ERROR("A04",HttpStatus.CONFLICT),
    NICKNAME_CODE_ERROR("A05",HttpStatus.CONFLICT),
    SOCIAL_NAME_ERROR("A07",HttpStatus.BAD_REQUEST),
    ACCESS_TOKEN_ERROR("A08",HttpStatus.BAD_REQUEST),
    WORKSPACE_CREATE_ERROR("A10",HttpStatus.MULTI_STATUS),
    WORKSPACE_NOT_FOUND_ERROR("A11",HttpStatus.NOT_FOUND),
    IMAGE_NOT_CREATED("A90",HttpStatus.NOT_ACCEPTABLE),
    NULL_ERROR(null, null);

    DeniedCode(String errorName, HttpStatus httpStatus) {
        this.errorCode = "DENIED_" + errorName;
        this.status = httpStatus;
    }
    DeniedCode(String errorName, int httpStatus) {
        this.errorCode = "DENIED_" + errorName;
        this.status = HttpStatus.valueOf(httpStatus);
    }

    public String code() {
        return this.errorCode;
    }

    public HttpStatus status() {
        return this.status;
    }

    private final String errorCode;
    private final HttpStatus status;
}
