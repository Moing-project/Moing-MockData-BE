package com.example.finalteammockdata.domain.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class AuthSignupRequestDto {
    @NotEmpty(message = "이메일이 존재하지 않습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식 오류")
    private final String email;
    @NotEmpty(message = "닉네임이 존재하지 않습니다.")
    @Pattern(regexp = "^[a-zA-Z\\d가-힣]{2,8}$", message = "닉네임 형식 오류")
    private final String nickname;
    @NotEmpty(message = "패스워드 존재하지 않습니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@!%*#?&])[A-Za-z\\d@!%*#?&]{8,}$", message = "비밀번호 형식 오류")
    private String password;

    public AuthSignupRequestDto(
             String email,
             String nickname,
             String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }
}
