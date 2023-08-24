package com.example.finalteammockdata.domain.auth.controller;

import com.example.finalteammockdata.domain.auth.dto.AuthEmailRequestDto;
import com.example.finalteammockdata.domain.auth.dto.AuthNicknameRequestDto;
import com.example.finalteammockdata.domain.auth.dto.AuthSignupRequestDto;
import com.example.finalteammockdata.domain.auth.handler.AuthExceptionHandler;
import com.example.finalteammockdata.domain.auth.service.AuthService;
import com.example.finalteammockdata.global.dto.MessageResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@Tag(name = "auth", description = "회원 API")
public class AuthController implements AuthExceptionHandler {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/signin")
    public ResponseEntity<MessageResponseDto> signIn(@RequestBody @Valid AuthSignupRequestDto requestDto){
        return ResponseEntity.ok(authService.signIn(requestDto));
    }

    @GetMapping("/nickname")
    public ResponseEntity<MessageResponseDto> checkNickname(@ModelAttribute @Valid AuthNicknameRequestDto nickname){
        return ResponseEntity.ok(authService.checkNickname(nickname.getNickname()));
    }

    @GetMapping("/email")
    public ResponseEntity<MessageResponseDto> checkUsername(@ModelAttribute @Valid AuthEmailRequestDto email){
        return ResponseEntity.ok(authService.checkUsername(email.getEmail()));
    }

    @GetMapping("/code")
    public ResponseEntity<MessageResponseDto> checkAuthCode(@RequestParam("code") Integer code){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(authService.checkAuthCode(code));
    }

    @GetMapping("/accessToken")
    public ResponseEntity<MessageResponseDto> returnAccessToken(HttpServletRequest request,
                                                                HttpServletResponse response){
        return ResponseEntity.ok(authService.returnAccessToken(request,response));
    }

}
