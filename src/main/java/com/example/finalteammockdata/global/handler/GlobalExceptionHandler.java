package com.example.finalteammockdata.global.handler;

import com.example.finalteammockdata.global.dto.BaseResponseDto;
import com.example.finalteammockdata.global.dto.MessageResponseDto;
import com.example.finalteammockdata.global.enums.DeniedCode;
import com.example.finalteammockdata.global.exception.DeniedCodeException;
import com.example.finalteammockdata.global.exception.ErrorCodeException;
import com.example.finalteammockdata.global.exception.GlobalStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalStateException.class)
    public ResponseEntity<MessageResponseDto> GlobalStateExceptionHandler(GlobalStateException exception){
        return ResponseEntity.status(exception.getStatus()).body(MessageResponseDto.out(exception.getStatus(), exception.getMessage()));
    }

    @ExceptionHandler(ErrorCodeException.class)
    public ResponseEntity<MessageResponseDto> GlobalStateExceptionHandler(ErrorCodeException exception){
        return ResponseEntity.ok(MessageResponseDto.out(exception.getStatus(), exception.getMessage()));
    }

    @ExceptionHandler(DeniedCodeException.class)
    public ResponseEntity<MessageResponseDto> GlobalStateExceptionHandler(DeniedCodeException exception){
        return ResponseEntity.ok(MessageResponseDto.out(exception.codeStatus(), exception.codeName()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDto<Map<String,String>>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        BaseResponseDto.BaseResponseDtoMessageBuilder messageBuilder = BaseResponseDto.messageBuilder().msg(DeniedCode.DO_VALID_ERROR.code());
        exception.getBindingResult().getFieldErrors().forEach(e -> messageBuilder.dataMsg(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.OK.value()).body(messageBuilder.build());
    }
}
