package com.example.finalteammockdata.domain.workspace.entity;

import com.example.finalteammockdata.domain.workspace.dto.WorkCreateRequestDto;
import com.example.finalteammockdata.domain.workspace.dto.WorkRequestDto;
import com.example.finalteammockdata.domain.workspace.enums.WorkAllowEnum;
import com.example.finalteammockdata.global.maps.Timestamped;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Workspace extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String subject; //분야

    private Integer totalMember; //인원

    private LocalDateTime lastTime; // 모집 마감 시간;

    private WorkAllowEnum allowType; // 멤버 참가 방식

    private String introduce;

    private String imageSrc;

    public Workspace() {

    }

    public Workspace(String name, String subject, Integer totalMember, LocalDateTime lastTime, WorkAllowEnum allowType, String introduce, String imageSrc) {
        this.name = name;
        this.subject = subject;
        this.totalMember = totalMember;
        this.lastTime = lastTime;
        this.allowType = allowType;
        this.introduce = introduce;
        this.imageSrc = imageSrc;
    }

    public Workspace(WorkCreateRequestDto createDto) {
        this.id=createDto.id();
        this.name = createDto.title();
        this.subject = createDto.subject();
        this.totalMember = createDto.totalMember();
        String[] date = createDto.date().split(".");
//        this.lastTime = LocalDateTime.of(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));
        this.allowType = WorkAllowEnum.get(createDto.allowType());
        this.introduce = createDto.introduce();
        this.imageSrc = createDto.imageSrc();
    }

    public void update(WorkRequestDto requestDtoDto) {
        this.name = requestDtoDto.title();
        this.subject = requestDtoDto.subject();
        this.totalMember = requestDtoDto.totalMember();
        String[] date = requestDtoDto.date().split(".");
        this.allowType = WorkAllowEnum.get(requestDtoDto.allowType());
        this.introduce = requestDtoDto.introduce();
        this.imageSrc = requestDtoDto.imageSrc();
        this.modifiedAt = LocalDateTime.now();
    }
}
