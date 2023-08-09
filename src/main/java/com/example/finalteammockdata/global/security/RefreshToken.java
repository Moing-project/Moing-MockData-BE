package com.example.finalteammockdata.global.security;

import com.example.finalteammockdata.global.maps.Timestamped;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken extends Timestamped {

    @Id
    private String loginId;

    private String refreshToken;

    @Builder
    public RefreshToken(String loginId, String refreshToken) {
        this.loginId = loginId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken updateToken(String refreshToken){
        this.refreshToken = refreshToken;
        return this;
    }
}