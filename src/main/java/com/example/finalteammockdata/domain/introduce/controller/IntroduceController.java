package com.example.finalteammockdata.domain.introduce.controller;

import com.example.finalteammockdata.domain.introduce.dto.IntroduceRequestDto;
import com.example.finalteammockdata.domain.introduce.dto.PageParam;
import com.example.finalteammockdata.domain.introduce.dto.StatusResponseDto;
import com.example.finalteammockdata.domain.introduce.service.IntroduceService;
import com.example.finalteammockdata.global.dto.BaseResponseDto;
import com.example.finalteammockdata.global.jwt.JwtUtil;
import com.example.finalteammockdata.global.sercurity.UserDetailsImpl;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Getter
@RequestMapping("/api/introduce")
@Slf4j(topic = "introduce controller")
public class IntroduceController {

    private final IntroduceService introduceService;

    private final JwtUtil jwtUtil;

    public IntroduceController(IntroduceService introduceService, JwtUtil jwtUtil){
        this.introduceService=introduceService;
        this.jwtUtil=jwtUtil;
    }

    @GetMapping("/team")
    public ResponseEntity<BaseResponseDto> getIntroduceByField(@ModelAttribute PageParam pageParam){
        return ResponseEntity.status(HttpStatus.OK).body(introduceService.getIntroduces(pageParam));
    }

    //<모집글 작성>
    @PostMapping("/team")
    public ResponseEntity<BaseResponseDto> createIntroduce(@RequestBody @Valid IntroduceRequestDto requestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(introduceService.createIntroduce(requestDto, userDetails));
    }

    //<모집글 수정>
    @PutMapping("/team/{id}")
    public ResponseEntity<BaseResponseDto> updatedIntroduce(@PathVariable Long id,
                                                            @RequestBody @Valid  IntroduceRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).body(introduceService.updatedIntroduce(id, requestDto, userDetails));
    }

    //<모집글 삭제>
    @DeleteMapping("/team/{id}")
    public ResponseEntity<StatusResponseDto> deletedIntroduce(@PathVariable Long id,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(introduceService.deleteIntroduce(id, userDetails), HttpStatus.OK);
    }
}
