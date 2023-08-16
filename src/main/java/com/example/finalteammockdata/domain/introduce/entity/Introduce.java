package com.example.finalteammockdata.domain.introduce.entity;


import com.example.finalteammockdata.domain.introduce.dto.IntroduceRequestDto;
import com.example.finalteammockdata.global.maps.Timestamped;
import com.example.finalteammockdata.global.sercurity.UserDetailsImpl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Introduce extends Timestamped {

    public Introduce(){

    }

    @Id
    @GeneratedValue
    public Long introduceId;

    @Column(name = "userId")
    public Long userId;

    @Column(name = "writer")
    private String writer;

    @Column(name = "teamName", nullable = false)
    private String teamName;

    @Column(name = "field", nullable = false)
    private String field;

    @Column(name = "recruitsNumber", nullable = false)
    private String recruitsNumber;

    @Column(name = "recruitmentPeriod", nullable = false)
    private String recruitmentPeriod;

    @Column(name = "participationWay", nullable = false)
    private String participationWay;

    @Column(name = "stack", nullable = false)
    private String stack;

    @Column(name = "introduceContent", nullable = false)
    public String introduceContent;

    @Column(name = "imageSrc")
    public String imageSrc;

    public Introduce(IntroduceRequestDto requestDto, UserDetailsImpl userDetails){
        this.userId=userDetails.getUser().getId();
        this.writer=userDetails.getUser().getNickname();
        this.teamName= requestDto.getTeamName();
        this.field=requestDto.getField();
        this.recruitsNumber = requestDto.getRecruitsNumber();
        this.recruitmentPeriod = requestDto.getRecruitmentPeriod();
        this.participationWay = requestDto.getParticipationWay();
        this.stack=requestDto.getStack();
        this.introduceContent= requestDto.getIntroduceContent();
        this.imageSrc= requestDto.getImageSrc();
    }

    public void update(IntroduceRequestDto requestDto){
        this.teamName= requestDto.getTeamName();
        this.field= requestDto.getField();
        this.recruitsNumber = requestDto.getRecruitsNumber();
        this.recruitmentPeriod = requestDto.getRecruitmentPeriod();
        this.participationWay = requestDto.getParticipationWay();
        this.stack = requestDto.getStack();
        this.introduceContent=requestDto.getIntroduceContent();
        this.imageSrc= requestDto.getImageSrc();
        this.modifiedAt= LocalDateTime.now();
    }

}
