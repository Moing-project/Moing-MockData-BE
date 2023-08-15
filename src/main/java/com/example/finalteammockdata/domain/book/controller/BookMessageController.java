package com.example.finalteammockdata.domain.book.controller;

import com.example.finalteammockdata.global.dto.BaseResponseDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class BookMessageController {

    @MessageMapping("/chat")
    @SendTo("/sub/channels/10")
    public BaseResponseDto<Map<String,String>> greeting(Map<String,String> msg) throws Exception {
        BaseResponseDto.BaseResponseDtoMessageBuilder messageBuilder = BaseResponseDto.messageBuilder();
        msg.forEach(messageBuilder::dataMsg);
        return messageBuilder.build();
    }
}
