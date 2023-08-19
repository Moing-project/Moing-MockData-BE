package com.example.finalteammockdata.domain.social.controller;

import com.example.finalteammockdata.global.dto.MessageResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/oauth/{social}")
public class SocialController {
    @GetMapping("")
    public ResponseEntity<MessageResponseDto> createSocial(@PathVariable String social){

        return null;
    }

    @GetMapping("/site")
    @ResponseBody
    public ResponseEntity<String> getSiteSocial(@PathVariable String social, HttpServletResponse response){
        log.info("get site social : {}", social);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        response.setContentType(MediaType.TEXT_HTML_VALUE);

//        MultiValueMap<String,String> headers = new MultiValueMap<>(null);
        String body = "<!doctype html><head><script>alert(\""+ social +"\");window.close()</script></head>";
//        ResponseEntity<String> responseEntity = new <>(body,)
//        return responseEntity
        return null;
    }
}
