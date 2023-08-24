package com.example.finalteammockdata.domain.workspace.dto;

import com.example.finalteammockdata.domain.auth.dto.AuthWorkSoloResponseDto;
import com.example.finalteammockdata.domain.workspace.entity.Workspace;
import com.example.finalteammockdata.domain.workspace.enums.WorkAllowEnum;

import java.time.LocalDateTime;
import java.util.List;

public class WorkResponseDto {
    private String name;

    private String subject; //분야

    private Integer totalMember; //모집 인원

    private LocalDateTime lastTime; // 모집 마감 시간;

    private WorkAllowEnum allowType; // 멤버 참가 방식

    private String introduce;

    private String imageSrc;

    private List<AuthWorkSoloResponseDto> members;

    public WorkResponseDto(String name, String subject, Integer totalMember, LocalDateTime lastTime, WorkAllowEnum allowType, String introduce, String imageSrc, List<AuthWorkSoloResponseDto> members) {
        this.name = name;
        this.subject = subject;
        this.totalMember = totalMember;
        this.lastTime = lastTime;
        this.allowType = allowType;
        this.introduce = introduce;
        this.imageSrc = imageSrc;
        this.members = members;
    }
    public WorkResponseDto(Workspace workspace, List<AuthWorkSoloResponseDto> members) {
        this.name = workspace.getName();
        this.subject = workspace.getSubject();
        this.totalMember = workspace.getTotalMember();
        this.lastTime = workspace.getLastTime();
        this.allowType = workspace.getAllowType();
        this.introduce = workspace.getIntroduce();
        this.imageSrc = workspace.getImageSrc();
        this.members = members;
    }

}
