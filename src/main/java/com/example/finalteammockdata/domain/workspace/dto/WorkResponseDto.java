package com.example.finalteammockdata.domain.workspace.dto;

import com.example.finalteammockdata.domain.auth.dto.AuthWorkSoloResponseDto;
import com.example.finalteammockdata.domain.workspace.entity.Workspace;
import com.example.finalteammockdata.domain.workspace.enums.WorkAllowEnum;
import com.example.finalteammockdata.domain.workspace.enums.WorkStackEnum;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class WorkResponseDto {
    private final String name;

    private final String subject; //분야

    private final Integer totalMember; //모집 인원

    private final LocalDateTime lastTime; // 모집 마감 시간;

    private final WorkAllowEnum allowType; // 멤버 참가 방식

    private final List<WorkStackEnum> stacks;

    private final String introduce;

    private final String imageSrc;

    private final List<AuthWorkSoloResponseDto> members;

    public WorkResponseDto(String name, String subject, Integer totalMember, LocalDateTime lastTime, WorkAllowEnum allowType, List<WorkStackEnum> stacks, String introduce, String imageSrc, List<AuthWorkSoloResponseDto> members) {
        this.name = name;
        this.subject = subject;
        this.totalMember = totalMember;
        this.lastTime = lastTime;
        this.allowType = allowType;
        this.stacks = stacks;
        this.introduce = introduce;
        this.imageSrc = imageSrc;
        this.members = members;
    }
    public WorkResponseDto(Workspace workspace, List<WorkStackEnum> stacks, List<AuthWorkSoloResponseDto> members) {
        this.name = workspace.getName();
        this.subject = workspace.getSubject().value();
        this.totalMember = workspace.getTotalMember();
        this.lastTime = workspace.getLastTime();
        this.allowType = workspace.getAllowType();
        this.introduce = workspace.getIntroduce();
        this.imageSrc = workspace.getImageSrc();
        this.stacks = stacks;
        this.members = members;
    }
}
