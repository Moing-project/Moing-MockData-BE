package com.example.finalteammockdata.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

@Schema(description = "이메일")
public class AuthEmailRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식 오류")
    @Schema(description = "이메일", example = "example@example.com")
    private final String email;

    public AuthEmailRequestDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
