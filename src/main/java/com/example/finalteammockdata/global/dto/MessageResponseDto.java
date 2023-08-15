package com.example.finalteammockdata.global.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MessageResponseDto {
    Integer status;
    String msg;
    public MessageResponseDto() {
    }
    protected MessageResponseDto(Integer status, String msg) {
        this.msg = msg;
        this.status = status;
    }

    protected MessageResponseDto(HttpStatus status, String msg) {
        this.msg = msg;
        this.status = status.value();
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static MessageResponseDto out(Integer status, String msg){
        return new MessageResponseDto(status, msg);
    }
    public static MessageResponseDto out(HttpStatus status, String msg){
        return new MessageResponseDto(status, msg);
    }
}
