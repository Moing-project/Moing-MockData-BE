package com.example.finalteammockdata.domain.auth.entity;

import com.example.finalteammockdata.domain.auth.dto.AuthSignupRequestDto;
import com.example.finalteammockdata.domain.auth.repository.AuthRepository;
import com.example.finalteammockdata.global.enums.AuthGlobalUserEnum;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class AuthTempUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AuthGlobalUserEnum userRole;

    private String nickname;

    protected AuthTempUser() {
    }

    public AuthTempUser(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.userRole = AuthGlobalUserEnum.FREE;
        this.nickname = nickname;
    }
    public AuthTempUser(AuthSignupRequestDto requestDto) {
        this(requestDto.getEmail(), requestDto.getPassword(), requestDto.getNickname());
    }
}
