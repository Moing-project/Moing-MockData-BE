package com.example.finalteammockdata.domain.auth.controller;

import com.example.finalteammockdata.domain.auth.dto.AuthLoginRequestDto;
import com.example.finalteammockdata.domain.auth.dto.AuthNicknameRequestDto;
import com.example.finalteammockdata.domain.auth.dto.AuthSignupRequestDto;
import com.example.finalteammockdata.domain.auth.handler.AuthExceptionHandler;
import com.example.finalteammockdata.domain.auth.service.AuthService;
import com.example.finalteammockdata.domain.auth.service.AuthServiceHelper;
import com.example.finalteammockdata.global.dto.BaseResponseDto;
import com.example.finalteammockdata.global.dto.MessageResponseDto;
import com.example.finalteammockdata.global.exception.ErrorCodeException;
import com.example.finalteammockdata.global.exception.GlobalStateException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import static com.example.finalteammockdata.global.enums.DeniedCode.NICKNAME_CORRECT_ERROR;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthExceptionHandler {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<MessageResponseDto> signIn(@RequestBody @Valid AuthSignupRequestDto requestDto){
        return ResponseEntity.ok(authService.signIn(requestDto));
    }

//    @PostMapping("/login")
//    public ResponseEntity<MessageResponseDto> login(@RequestBody AuthLoginRequestDto requestDto){
//        return ResponseEntity.ok(authService.login(requestDto));
//    }

    @GetMapping("/nickname")
    public ResponseEntity<MessageResponseDto> checkNickname(@ModelAttribute @Valid AuthNicknameRequestDto nickname){
        return ResponseEntity.ok(authService.checkNickname(nickname.getNickname()));
    }

    @GetMapping("/email")
    @Valid
    public ResponseEntity<MessageResponseDto> checkUsername(@RequestParam("email")
                                                                @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식 오류")
                                                                String email){
        return ResponseEntity.ok(authService.checkUsername(email));
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
