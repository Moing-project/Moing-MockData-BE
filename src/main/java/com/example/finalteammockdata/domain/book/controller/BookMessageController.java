package com.example.finalteammockdata.domain.book.controller;

import com.example.finalteammockdata.global.dto.BaseResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Slf4j
@Controller
public class BookMessageController {

    private final SimpMessageSendingOperations sendingOperations;

    public BookMessageController(SimpMessageSendingOperations sendingOperations) {
        this.sendingOperations = sendingOperations;
    }

    @MessageMapping("/chat")
    public void greeting(Map<String, String> msg) {
        BaseResponseDto.BaseResponseDtoMessageBuilder messageBuilder = BaseResponseDto.messageBuilder();
        msg.forEach((k, v) -> {
            log.info("{} : {}", k, v);
            messageBuilder.dataMsg(k, v);
        });
        sendingOperations.convertAndSend("/sub/channels/10", messageBuilder.build());
    }
}
