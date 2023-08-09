package com.example.finalteammockdata.global.security.jwt;

import com.example.finalteammockdata.global.dto.JwtTokenDto;
import com.example.finalteammockdata.global.exception.ErrorCode;
import com.example.finalteammockdata.global.exception.RequestException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        log.info("request URI : '{}'", request.getRequestURI());
        log.info("request QueryString : '{}'", request.getQueryString());
        log.info("request Method : '{}'", request.getMethod());

        JwtTokenDto jwtTokenDto = getAccessToken(request);
        String accessToken = jwtTokenDto == null ? null : jwtTokenDto.getAccessToken();
        String refreshToken = jwtTokenDto == null ? null : jwtTokenDto.getRefreshToken();
        String requestURI = request.getRequestURI();

        if (StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
            Authentication authentication = tokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("AccessToken으로 Security Context에 Member: '{}' 인증 정보를 저장했습니다. URI: '{}'", authentication.getName(),
                    requestURI);
        } else if (StringUtils.hasText(accessToken)) {
            log.debug("access 토큰이 만료되었습니다.");
            throw new RequestException(ErrorCode.JWT_UNAUTHORIZED_401);
        } else if (StringUtils.hasText(refreshToken) && tokenProvider.validateToken(refreshToken)) {
            Authentication authentication = tokenProvider.getAuthentication(refreshToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("RefreshToken으로 Security Context에 Member: '{}' 인증 정보를 저장했습니다. URI: '{}'",
                    authentication.getName(), requestURI);
        } else if (StringUtils.hasText(refreshToken)) {
            log.debug("refresh 토큰이 만료되었습니다.");
            throw new RequestException(ErrorCode.JWT_UNAUTHORIZED_401);
        }

        filterChain.doFilter(request, response);
    }

    private JwtTokenDto getAccessToken(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }

        String accessToken = null;
        String refreshToken = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("accessToken")) {
                accessToken = cookie.getValue();
            }
            if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
            }
        }

        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
    }
}
