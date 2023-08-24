package com.example.finalteammockdata.domain.auth.dto;

import com.example.finalteammockdata.domain.auth.dao.AuthNickAndImageDao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "사용자 프로필")
public class AuthWorkSoloResponseDto {

    private final Long userId;
    private final String profileImage;
    private final String nickname;

    public AuthWorkSoloResponseDto(Long userId, String profileImage, String nickname) {
        this.userId = userId;
        this.profileImage = profileImage;
        this.nickname = nickname;
    }

    public AuthWorkSoloResponseDto(AuthNickAndImageDao authNickAndImageDao) {
        this.userId = authNickAndImageDao.userId();
        this.profileImage = authNickAndImageDao.imageSrc();
        this.nickname = authNickAndImageDao.nickname();
    }
}
