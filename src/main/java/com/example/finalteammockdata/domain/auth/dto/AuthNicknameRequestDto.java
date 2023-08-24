package com.example.finalteammockdata.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AuthNicknameRequestDto {

    @NotEmpty(message = "닉네임이 존재하지 않습니다.")
    @Pattern(regexp = "^[a-zA-Z\\d가-힣]{2,8}$", message = "닉네임 형식 오류")
    @Schema(description = "닉네임", example = "Test")
    private final String nickname;

    public AuthNicknameRequestDto(String nickname) {
        this.nickname = nickname;
    }
}
