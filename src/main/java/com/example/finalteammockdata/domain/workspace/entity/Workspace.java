package com.example.finalteammockdata.domain.workspace.entity;

import com.example.finalteammockdata.domain.workspace.dto.WorkCreateDto;
import com.example.finalteammockdata.domain.workspace.enums.WorkAllowMember;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String subject; //분야

    Integer needMember; //모집 인원

    LocalDateTime lastTime; // 모집 마감 시간;

    WorkAllowMember allowType; // 멤버 참가 방식

    String introduce;

    String imageSrc;

    public Workspace() {

    }

    public Workspace(String name, String subject, Integer needMember, LocalDateTime lastTime, WorkAllowMember allowType, String introduce, String imageSrc) {
        this.name = name;
        this.subject = subject;
        this.needMember = needMember;
        this.lastTime = lastTime;
        this.allowType = allowType;
        this.introduce = introduce;
        this.imageSrc = imageSrc;
    }

    public Workspace(WorkCreateDto createDto) {
        this.name = createDto.title();
        this.subject = createDto.subject();
        this.needMember = createDto.needMember();
        this.lastTime = LocalDateTime.parse(createDto.date());
        this.allowType = WorkAllowMember.get(createDto.allowType());
        this.introduce = createDto.introduce();
        this.imageSrc = createDto.imageSrc();
    }
}
