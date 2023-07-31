package com.example.finalteammockdata.domain.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuthSignupRequestDto {
    private final String username;
    private final String nickname;
    private final String password;

    public AuthSignupRequestDto(
            @NotNull(message = "패스워드 존재하지 않습니다.") String username,
            @NotNull(message = "닉네임이 존재하지 않습니다.") String nickname,
            @NotNull(message = "패스워드 존재하지 않습니다.") String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }
}
