package com.example.finalteammockdata.domain.auth.controller;

import com.example.finalteammockdata.domain.auth.dto.AuthLoginRequestDto;
import com.example.finalteammockdata.domain.auth.dto.AuthSignupRequestDto;
import com.example.finalteammockdata.domain.auth.handler.AuthExceptionHandler;
import com.example.finalteammockdata.domain.auth.service.AuthService;
import com.example.finalteammockdata.domain.social.dto.SocialUserInfoDto;
import com.example.finalteammockdata.domain.social.service.SocialLoginService;
import com.example.finalteammockdata.domain.social.service.SocialLoginServiceMap;
import com.example.finalteammockdata.global.dto.BaseResponseDto;
import com.example.finalteammockdata.global.dto.JwtTokenDto;
import com.example.finalteammockdata.global.dto.ResResultDto;
import com.example.finalteammockdata.global.exception.ErrorCode;
import com.example.finalteammockdata.global.exception.RequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthExceptionHandler {

    private final AuthService authService;
    private final SocialLoginServiceMap socialLoginServiceMap;

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
    @PostMapping("/{social}")
    public ResponseEntity<ResResultDto> login(
            @PathVariable("social") String socialPath, @RequestParam(name = "code") String code, String state,
            HttpServletResponse response) throws JsonProcessingException {

        SocialLoginService socialLoginService = socialLoginServiceMap.get(socialPath);
        if (socialLoginService == null) {
            throw new RequestException(ErrorCode.COMMON_BAD_REQUEST_400);
        }

        SocialUserInfoDto socialUserInfoDto = socialLoginService.socialLogin(code, state);
        if (socialUserInfoDto == null) {
            throw new RequestException(ErrorCode.COMMON_BAD_REQUEST_400);
        }

        String loginId = authService.login(socialUserInfoDto);
        JwtTokenDto jwtTokenDto = authService.getJwtTokenDto(loginId);

        authService.setJwtCookie(response, jwtTokenDto);

        return ResponseEntity.ok(new ResResultDto("로그인 성공"));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ResResultDto> reissue(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        String refreshToken = null;

        if (cookies == null) {
            throw new RequestException(ErrorCode.JWT_NOT_FOUND_404);
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
            }
        }

        if (refreshToken == null) {
            throw new RequestException(ErrorCode.JWT_NOT_FOUND_404);
        }

        JwtTokenDto jwtTokenDto = authService.reissue(JwtTokenDto.builder()
                .refreshToken(refreshToken)
                .build());

        authService.setJwtCookie(response, jwtTokenDto);

        return ResponseEntity.ok(new ResResultDto("JWT 갱신 완료"));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<ResResultDto> logout(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return ResponseEntity.ok(new ResResultDto("로그아웃 성공"));
        }

        for (Cookie cookie : cookies) {
            log.info("cookie name: '{}'", cookie.getName());
            log.info("cookie value: '{}'", cookie.getValue());
            log.info("cookie domain: '{}'", cookie.getDomain());
        }

        ResponseCookie responseCookie = ResponseCookie.from("accessToken", "")
                .domain("cooperate-up.com")
                .httpOnly(false)
                .maxAge(0)
                .sameSite("None")
                .secure(true)
                .path("/").build();

        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        responseCookie = ResponseCookie.from("refreshToken", "")
                .domain("cooperate-up.com")
                .httpOnly(false)
                .maxAge(0)
                .sameSite("None")
                .secure(true)
                .path("/").build();

        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        authService.logout(loginId);

        return ResponseEntity.ok(new ResResultDto("로그아웃 성공"));
    }
}
