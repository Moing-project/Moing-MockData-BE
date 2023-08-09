package com.example.finalteammockdata.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Social {

    KAKAO("kakao"),
    NAVER("naver"),
    GOOGLE("google");

    private final String social;
}
