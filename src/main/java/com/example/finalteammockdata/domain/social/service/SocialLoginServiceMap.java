package com.example.finalteammockdata.domain.social.service;

import com.example.finalteammockdata.global.enums.Social;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SocialLoginServiceMap extends HashMap<String, SocialLoginService> {

    public SocialLoginServiceMap(AuthGoogleService authGoogleService,
                                 AuthKakaoService authKakaoService,
                                 AuthNaverService authNaverService) {

        this.put(Social.GOOGLE.getSocial(), authGoogleService);
        this.put(Social.KAKAO.getSocial(), authKakaoService);
        this.put(Social.NAVER.getSocial(), authNaverService);
    }
}