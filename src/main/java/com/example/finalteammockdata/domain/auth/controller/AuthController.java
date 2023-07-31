package com.example.finalteammockdata.domain.auth.controller;

import com.example.finalteammockdata.domain.auth.dto.AuthLoginRequestDto;
import com.example.finalteammockdata.domain.auth.dto.AuthSignupRequestDto;
import com.example.finalteammockdata.domain.auth.handler.AuthExceptionHandler;
import com.example.finalteammockdata.domain.auth.service.AuthService;
import com.example.finalteammockdata.global.dto.BaseResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthExceptionHandler {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<BaseResponseDto> signIn(@RequestBody @Valid AuthSignupRequestDto requestDto){
        return ResponseEntity.status(201).body(authService.signIn(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDto> login(@RequestBody AuthLoginRequestDto requestDto){
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @GetMapping("/nickname")
    public ResponseEntity<BaseResponseDto> checkNickname(@RequestParam("nickname") String nickname){
        return ResponseEntity.ok(authService.checkNickname(nickname));
    }

    @GetMapping("/username")
    public ResponseEntity<BaseResponseDto> checkUsername(@RequestParam("username") String username){
        return ResponseEntity.ok(authService.checkUsername(username));
    }

}
