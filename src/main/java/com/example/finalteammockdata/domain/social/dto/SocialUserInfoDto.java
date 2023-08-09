package com.example.finalteammockdata.domain.social.dto;

import com.example.finalteammockdata.global.enums.Social;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialUserInfoDto {

    private String loginId;
    private String nickname;
    private String profileImage;
    private Social social;
}
