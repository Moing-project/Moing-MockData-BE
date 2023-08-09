package com.example.finalteammockdata.domain.social.service;

import com.example.finalteammockdata.domain.social.dto.SocialUserInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface SocialLoginService {
    SocialUserInfoDto socialLogin(String code, String state) throws JsonProcessingException;

    String getAccessToken(String code, String state) throws JsonProcessingException;

    SocialUserInfoDto getUserInfo(String accessToken) throws JsonProcessingException;
}
