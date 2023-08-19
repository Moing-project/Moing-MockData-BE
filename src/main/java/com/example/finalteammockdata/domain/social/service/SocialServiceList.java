package com.example.finalteammockdata.domain.social.service;

import com.example.finalteammockdata.global.enums.DeniedCode;
import com.example.finalteammockdata.global.exception.ErrorCodeException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.finalteammockdata.global.enums.DeniedCode.SOCIAL_NAME_ERROR;

@Component
public class SocialServiceList {

    private final Map<SocialEnum, SocialService> SocialList;

    public SocialServiceList(SocialServiceKakao kakao) {
        SocialList = new HashMap<>();
        SocialList.put(SocialEnum.KAKAO,kakao);
    }

    public SocialService getSocialService(String social){
        SocialEnum socialEnum = SocialEnum.gets(social);
        if(socialEnum == null)
            throw ErrorCodeException.make(SOCIAL_NAME_ERROR.status(), SOCIAL_NAME_ERROR.code());
        return SocialList.get(socialEnum);
    }

    public enum SocialEnum{
        KAKAO;
        public static SocialEnum gets(String name){
            for (SocialEnum value : values()) {
                if(value.name().equalsIgnoreCase(name))
                    return value;
            }
            return null;
        }
    }
}
