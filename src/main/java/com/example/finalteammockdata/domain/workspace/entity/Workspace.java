package com.example.finalteammockdata.domain.workspace.entity;

import com.example.finalteammockdata.domain.workspace.dto.WorkCreateRequestDto;
import com.example.finalteammockdata.domain.workspace.enums.WorkAllowEnum;
import com.example.finalteammockdata.domain.workspace.enums.WorkSubjectEnum;
import com.example.finalteammockdata.global.maps.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Workspace extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private WorkSubjectEnum subject; // 분야

    private Integer totalMember; //모집 인원

    private LocalDateTime lastTime; // 모집 마감 시간;

    @Enumerated(value = EnumType.STRING)
    private WorkAllowEnum allowType; // 멤버 참가 방식

    private String introduce;

    private String imageSrc;

    public Workspace() {

    }

    public Workspace(String name, WorkSubjectEnum subject, Integer totalMember, LocalDateTime lastTime, WorkAllowEnum allowType, String introduce, String imageSrc) {
        this.name = name;
        this.subject = subject;
        this.totalMember = totalMember;
        this.lastTime = lastTime;
        this.allowType = allowType;
        this.introduce = introduce;
        this.imageSrc = imageSrc;
    }

    public Workspace(WorkCreateRequestDto createDto) {
        this.name = createDto.title();
        this.subject = WorkSubjectEnum.get(createDto.subject());
        this.totalMember = createDto.totalMember();
        String[] date = createDto.date().split("\\.");
        this.lastTime = LocalDateTime.of(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2])-1, 23,59);
        this.allowType = WorkAllowEnum.get(createDto.allowType());
        this.introduce = createDto.introduce();
        this.imageSrc = createDto.imageSrc();
    }
}
