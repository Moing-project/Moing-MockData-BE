package com.example.finalteammockdata.domain.auth.handler;

import com.example.finalteammockdata.domain.auth.exception.AuthDuplicationException;
import com.example.finalteammockdata.global.dto.BaseResponseDto;
import com.example.finalteammockdata.global.dto.MessageResponseDto;
import jakarta.security.auth.message.AuthException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.slf4j.LoggerFactory.getLogger;

public interface AuthExceptionHandler {

    @ExceptionHandler(AuthDuplicationException.class)
    default ResponseEntity<MessageResponseDto> postExceptionHandler(AuthDuplicationException e) {
        MessageResponseDto response = MessageResponseDto.out(e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    private static Logger logger() {
        final class LogHolder {
            private static final Logger LOGGER = getLogger(AuthExceptionHandler.class);
        }
        return LogHolder.LOGGER;
    }
}
