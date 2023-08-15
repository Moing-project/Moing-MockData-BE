package com.example.finalteammockdata.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseDto <T> extends MessageResponseDto{

    private T data;

    private BaseResponseDto(Integer status, String msg, T data) {
        super(status, msg);
        this.data = data;
    }

    private BaseResponseDto() {
    }

    public static BaseResponseDtoBuilder builder(){
        return new BaseResponseDtoBuilder();
    }

    public static BaseResponseDtoMessageBuilder messageBuilder(){
        return new BaseResponseDtoMessageBuilder();
    }

    public static class BaseResponseDtoBuilder{
        private final BaseResponseDto instance;
        public BaseResponseDtoBuilder() {
            this.instance = new BaseResponseDto();
        }
        public BaseResponseDtoBuilder msg(String msg){
            this.instance.msg = msg;
            return this;
        }

        public <T> BaseResponseDtoBuilder data(T data){
            this.instance.data = data;
            return this;
        }
        public BaseResponseDto build(){
            return this.instance;
        }
    }

    public static class BaseResponseDtoMessageBuilder{
        private final BaseResponseDto<Map<String, String>> instance;
        public BaseResponseDtoMessageBuilder() {
            this.instance = new BaseResponseDto<Map<String, String>>();
            this.instance.data = new LinkedHashMap<>();
        }
        public BaseResponseDtoMessageBuilder msg(String msg){
            this.instance.msg = msg;
            return this;
        }

        public BaseResponseDtoMessageBuilder dataMsg(String key, String value){
            this.instance.data.put(key, value);
            return this;
        }
        public BaseResponseDto<Map<String, String>> build(){
            return this.instance;
        }
    }
}
