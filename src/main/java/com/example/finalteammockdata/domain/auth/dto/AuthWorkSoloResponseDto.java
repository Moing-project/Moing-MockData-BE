package com.example.finalteammockdata.domain.auth.dto;

import com.example.finalteammockdata.domain.auth.dao.AuthNickAndImageDao;
import lombok.Getter;

@Getter

public class AuthWorkSoloResponseDto {
    private final String profileImage;
    private final String nickname;

    public AuthWorkSoloResponseDto(String profileImage, String nickname) {
        this.profileImage = profileImage;
        this.nickname = nickname;
    }

    public AuthWorkSoloResponseDto(AuthNickAndImageDao authNickAndImageDao) {
        this.profileImage = authNickAndImageDao.imageSrc();
        this.nickname = authNickAndImageDao.nickname();
    }
}
