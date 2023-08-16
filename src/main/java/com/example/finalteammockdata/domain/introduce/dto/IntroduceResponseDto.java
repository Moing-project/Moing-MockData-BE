package com.example.finalteammockdata.domain.introduce.dto;

import com.example.finalteammockdata.domain.introduce.entity.Introduce;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class IntroduceResponseDto {

    private Long introduceId;

    private String writer;

    private String teamName; // 이름

    private String field; // 프로젝트 분야

    private String recruitsNumber; // 모집 인원

    private String recruitmentPeriod; // 모집 기간

    private String participationWay; // 참여 방법

    private String stack; // 스택

    private String introduceContent; // 소개

    private String imageSrc; // 이미지

    private String createdAt;

    private String modifiedAt;

    public IntroduceResponseDto(Introduce introduce){
        this.introduceId=introduce.getIntroduceId();
        this.writer=introduce.getWriter();
        this.teamName=introduce.getTeamName();
        this.field=introduce.getField();
        this.recruitsNumber=introduce.getRecruitsNumber();
        this.recruitmentPeriod=introduce.getRecruitmentPeriod();
        this.participationWay=introduce.getParticipationWay();
        this.stack=introduce.getStack();
        this.introduceContent=introduce.getIntroduceContent();
        this.imageSrc=introduce.getImageSrc();
        this.createdAt=introduce.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.modifiedAt=introduce.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}
