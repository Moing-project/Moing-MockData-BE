package com.example.finalteammockdata.global.exception;

import com.example.finalteammockdata.global.enums.DeniedCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DeniedCodeException extends RuntimeException{
    private final DeniedCode code;

    private DeniedCodeException(DeniedCode code) {
        this.code = code;
    }

    public DeniedCode deniedCode(){
        return this.code;
    }
    public String codeName(){
        return this.code.code();
    }
    public HttpStatus codeStatus(){
        return this.code.status();
    }
    public static DeniedCodeException out(DeniedCode code){
        return new DeniedCodeException(code);
    }
}
