package com.example.finalteammockdata.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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
    private BaseResponseDto(T data) {
        this.data = data;
    }

    public static <T> BaseResponseDtoBuilder<T> builder(T data){
        return new BaseResponseDtoBuilder(data);
    }

    public static BaseResponseDtoMessageBuilder messageBuilder(){
        return new BaseResponseDtoMessageBuilder();
    }

    public static class BaseResponseDtoBuilder <T> {
        private final BaseResponseDto<T> instance;
        public BaseResponseDtoBuilder(T data) {
            this.instance = new BaseResponseDto<T>(data);
        }
        public BaseResponseDtoBuilder<T> msg(String msg){
            this.instance.msg = msg;
            return this;
        }
        public BaseResponseDtoBuilder<T> status(Integer status){
            this.instance.status = status;
            return this;
        }
        public BaseResponseDtoBuilder<T> status(HttpStatus status){
            this.instance.status = status.value();
            return this;
        }
        public BaseResponseDto<T> build(){
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
        public BaseResponseDtoMessageBuilder status(Integer status){
            this.instance.status = status;
            return this;
        }
        public BaseResponseDtoMessageBuilder status(HttpStatus status){
            this.instance.status = status.value();
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
