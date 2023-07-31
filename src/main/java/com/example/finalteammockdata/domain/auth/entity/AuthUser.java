package com.example.finalteammockdata.domain.auth.entity;

import com.example.finalteammockdata.domain.auth.dto.AuthSignupRequestDto;
import com.example.finalteammockdata.global.enums.AuthGlobalUserEnum;
import com.example.finalteammockdata.global.maps.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class AuthUser extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AuthGlobalUserEnum userRole;

    private String nickname;

    private String profile_image;


    public AuthUser() {
    }

    public AuthUser(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.userRole = AuthGlobalUserEnum.FREE;
        this.nickname = nickname;
    }

    public AuthUser(AuthSignupRequestDto requestDto) {
        this(requestDto.getUsername(), requestDto.getPassword(), requestDto.getNickname());
    }

}
