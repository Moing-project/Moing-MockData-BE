package com.example.finalteammockdata.domain.introduce.dto;

import lombok.Getter;

@Getter
public class StatusResponseDto {
    private String message;
    private int statusCode;

    public StatusResponseDto(String message,int statusCode){
        this.message=message;
        this.statusCode=statusCode;
    }
}
