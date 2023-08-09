package com.example.finalteammockdata.domain.auth.service;

import com.example.finalteammockdata.domain.auth.dto.AuthLoginRequestDto;
import com.example.finalteammockdata.domain.auth.dto.AuthSignupRequestDto;
import com.example.finalteammockdata.domain.auth.entity.AuthUser;
import com.example.finalteammockdata.domain.auth.exception.AuthDuplicationException;
import com.example.finalteammockdata.domain.auth.repository.AuthRepository;
import com.example.finalteammockdata.domain.social.dto.SocialUserInfoDto;
import com.example.finalteammockdata.domain.social.repository.MemberRepository;
import com.example.finalteammockdata.global.dto.BaseResponseDto;
import com.example.finalteammockdata.global.dto.JwtTokenDto;
import com.example.finalteammockdata.global.enums.Social;
import com.example.finalteammockdata.global.exception.ErrorCode;
import com.example.finalteammockdata.global.exception.RequestException;
import com.example.finalteammockdata.global.repository.RefreshTokenRepository;
import com.example.finalteammockdata.global.security.RefreshToken;
import com.example.finalteammockdata.global.security.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public BaseResponseDto signIn(AuthSignupRequestDto requestDto) {

        if(authRepository.findByUsernameExist(requestDto.getUsername())){
            throw new AuthDuplicationException(409, "아이디가 중복되었습니다.");
        }
        if(authRepository.findByNicknameExist(requestDto.getNickname())){
            throw new AuthDuplicationException(409, "닉네임이 중복되었습니다.");
        }

        AuthUser newUser = new AuthUser(requestDto);
        authRepository.save(newUser);
        return BaseResponseDto.builder().msg("success").build();
    }

    public BaseResponseDto checkNickname(String nickname) {
        if(!StringUtils.hasText(nickname))
            throw new AuthDuplicationException(404, "닉네임을 적어주십시오.");
        if(authRepository.findByNicknameExist(nickname))
            throw new AuthDuplicationException(409, "닉네임이 중복되었습니다.");
        return BaseResponseDto.builder().msg("success").build();
    }

    public BaseResponseDto checkUsername(String username) {
        if(!StringUtils.hasText(username))
            throw new AuthDuplicationException(404, "아이디를 적어주십시오.");
        if(authRepository.findByUsernameExist(username))
            throw new AuthDuplicationException(409, "아이디가 중복되었습니다.");
        return BaseResponseDto.builder().msg("success").build();
    }

    public BaseResponseDto login(AuthLoginRequestDto requestDto) {
        String password = authRepository.findByPasswordInUsername(requestDto.username());
        if(password == null)
            throw new AuthDuplicationException(401, "아이디를 찾을 수 없습니다.");
        if(password.equals(requestDto.password()))
            return BaseResponseDto.builder().msg("success").build();
        throw new AuthDuplicationException(401, "비밀번호가 틀립니다.");
    }

    @Transactional
    public JwtTokenDto getJwtTokenDto(String loginId) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginId, loginId);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtTokenDto jwtTokenDto = tokenProvider.generateTokenDto(authentication);

        // refresh 토큰이 데이터베이스에 있다면 갱신하고, 없다면 새로 저장.
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByLoginId(loginId);
        if (refreshToken.isPresent()) {
            refreshToken.get().updateToken(jwtTokenDto.getRefreshToken());
        } else {
            refreshTokenRepository.save(RefreshToken.builder()
                    .loginId(loginId)
                    .refreshToken(jwtTokenDto.getRefreshToken())
                    .build());
        }

        return jwtTokenDto;
    }

    @Transactional
    public JwtTokenDto reissue(JwtTokenDto jwtTokenDto) {

        //fixme refresh 토큰이 만료일 경우 쿠키 삭제, 데이터베이스 삭제 등 처리가 필요하다.
        String refreshToken = jwtTokenDto.getRefreshToken();
        if (!tokenProvider.validateToken(refreshToken)) {
            String loginId = tokenProvider.getLoginId(refreshToken);
            refreshTokenRepository.deleteByLoginId(loginId);

            throw new RequestException(ErrorCode.JWT_UNAUTHORIZED_401);
        }

        // 사용자의 데이터베이스에 저장된 토큰 조회
        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByLoginId(authentication.getName());

        // 데이터베이스에 토큰이 없다면, 로그아웃된 사용자
        if (!optionalRefreshToken.isPresent()) {
            throw new RequestException(ErrorCode.JWT_NOT_FOUND_404);
        }

        // 토큰정보 불일치.
        if (!optionalRefreshToken.get().getRefreshToken().equals(refreshToken)) {
            throw new RequestException(ErrorCode.JWT_NOT_ALLOWED_405);
        }

        JwtTokenDto newJwtTokenDto = tokenProvider.generateTokenDto(authentication);

        optionalRefreshToken.get().updateToken(newJwtTokenDto.getRefreshToken());

        return newJwtTokenDto;
    }

    @Transactional
    public void logout(String loginId) {
        refreshTokenRepository.deleteByLoginId(loginId);
    }

    public SocialUserInfoDto google(String code) {

        Social social = Social.GOOGLE;
        return generateSocialUserInfoDto(null, null, null, social);
    }

    public SocialUserInfoDto generateSocialUserInfoDto(String loginId, String nickname, String profileImage,
                                                       Social social) {
        return SocialUserInfoDto.builder()
                .loginId(loginId)
                .nickname(nickname)
                .profileImage(profileImage)
                .social(social).build();
    }

    public void setJwtCookie(HttpServletResponse response, JwtTokenDto jwtTokenDto) {

        ResponseCookie responseCookie = ResponseCookie.from("accessToken", jwtTokenDto.getAccessToken())
                .domain("cooperate-up.com")
                .httpOnly(false)
                .maxAge(60 * 30)
                .sameSite("None")
                .secure(true)
                .path("/").build();

        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        responseCookie = ResponseCookie.from("refreshToken", jwtTokenDto.getRefreshToken())
                .domain("cooperate-up.com")
                .httpOnly(false)
                .maxAge(60 * 60 * 24)
                .sameSite("None")
                .secure(true)
                .path("/").build();

        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }
}
