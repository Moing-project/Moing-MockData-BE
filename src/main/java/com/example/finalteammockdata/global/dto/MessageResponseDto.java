package com.example.finalteammockdata.global.dto;

import com.example.finalteammockdata.global.enums.AccessCode;
import com.example.finalteammockdata.global.enums.DeniedCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MessageResponseDto {
    Integer status;
    String msg;
    public MessageResponseDto() {
    }
    public MessageResponseDto(AccessCode accessCode) {
        this.msg = accessCode.code();
        this.status = accessCode.status().value();
    }
    public MessageResponseDto(DeniedCode deniedCode) {
        this.msg = deniedCode.code();
        this.status = deniedCode.status().value();
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

    public static MessageResponseDto out(AccessCode code){
        return new MessageResponseDto(code);
    }
    public static MessageResponseDto out(DeniedCode code){
        return new MessageResponseDto(code);
    }

}
