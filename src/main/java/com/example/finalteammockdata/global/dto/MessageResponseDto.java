package com.example.finalteammockdata.global.dto;

import lombok.Getter;

@Getter
public class MessageResponseDto {
    String msg;

    protected MessageResponseDto(String msg) {
        this.msg = msg;
    }

    public MessageResponseDto() {
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static MessageResponseDto out(String msg){
        return new MessageResponseDto(msg);
    }
}
