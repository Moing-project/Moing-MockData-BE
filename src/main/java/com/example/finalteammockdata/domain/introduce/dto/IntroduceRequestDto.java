package com.example.finalteammockdata.domain.introduce.dto;

import com.example.finalteammockdata.domain.introduce.entity.Introduce;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class IntroduceRequestDto {
    @NotBlank
    @Size(min = 1, max = 255, message = "팀명은 1자 ~ 255자 내에서 작성해주세요.")
    private String teamName;

    @NotBlank
    private String field;

    @NotBlank
    @Size(min = 1, max = 2, message = "모집멤버는 1명 ~ 99명 사이로 설정해주세요.")
    private String recruitsNumber;

    @NotBlank
    private String recruitmentPeriod;

    @NotNull
    private String participationWay;

    private String stack;

    @NotNull
    @Size(min = 0, max = 1000, message = "소개글은 1000자 내로 작성해주세요")
    private String introduceContent;

    private String imageSrc;

}
