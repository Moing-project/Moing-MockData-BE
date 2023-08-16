package com.example.finalteammockdata.domain.workspace.dto;

import com.example.finalteammockdata.domain.workspace.enums.WorkAllowEnum;

import java.time.LocalDateTime;

public class WorkResponseDto {
    private String name;

    private String subject; //분야

    private Integer needMember; //모집 인원

    private LocalDateTime lastTime; // 모집 마감 시간;

    private WorkAllowEnum allowType; // 멤버 참가 방식

    private String introduce;

    private String imageSrc;
}
