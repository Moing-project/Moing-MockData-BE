package com.example.finalteammockdata.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalStateException extends RuntimeException{
    HttpStatus status;

    private GlobalStateException(String message) {
        super(message);
    }

    public GlobalStateException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static GlobalStateExceptionBuilder builder(String message){
        return new GlobalStateExceptionBuilder(new GlobalStateException(message));
    }

    public static class GlobalStateExceptionBuilder{
        private final GlobalStateException instance;

        public GlobalStateExceptionBuilder(GlobalStateException instance) {
            this.instance = instance;
        }

        public GlobalStateExceptionBuilder status(int status){
            this.instance.status = HttpStatus.valueOf(status);
            return this;
        }

        public GlobalStateExceptionBuilder status(HttpStatus status){
            this.instance.status = status;
            return this;
        }
        public GlobalStateException build(){
            return this.instance;
        }
    }
}
