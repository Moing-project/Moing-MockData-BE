package com.example.finalteammockdata.domain.workspace.entity;

import com.example.finalteammockdata.domain.workspace.dto.WorkCreateRequestDto;
import com.example.finalteammockdata.domain.workspace.enums.WorkAllowEnum;
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
    private Long id;

    private String name;

    private String subject; //분야

    private Integer needMember; //모집 인원

    private LocalDateTime lastTime; // 모집 마감 시간;

    private WorkAllowEnum allowType; // 멤버 참가 방식

    private String introduce;

    private String imageSrc;

    public Workspace() {

    }

    public Workspace(String name, String subject, Integer needMember, LocalDateTime lastTime, WorkAllowEnum allowType, String introduce, String imageSrc) {
        this.name = name;
        this.subject = subject;
        this.needMember = needMember;
        this.lastTime = lastTime;
        this.allowType = allowType;
        this.introduce = introduce;
        this.imageSrc = imageSrc;
    }

    public Workspace(WorkCreateRequestDto createDto) {
        this.name = createDto.title();
        this.subject = createDto.subject();
        this.needMember = createDto.needMember();
        String[] date = createDto.date().split(".");
//        this.lastTime = LocalDateTime.of(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));
        this.allowType = WorkAllowEnum.get(createDto.allowType());
        this.introduce = createDto.introduce();
        this.imageSrc = createDto.imageSrc();
    }
}
