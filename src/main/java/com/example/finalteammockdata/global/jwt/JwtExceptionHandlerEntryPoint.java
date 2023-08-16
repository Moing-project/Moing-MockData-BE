package com.example.finalteammockdata.global.jwt;

import com.example.finalteammockdata.global.dto.BaseResponseDto;
import com.example.finalteammockdata.global.dto.BaseResponseDto.BaseResponseDtoMessageBuilder;
import com.example.finalteammockdata.global.enums.DeniedCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtExceptionHandlerEntryPoint extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            setResponse(response,"Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            setResponse(response,"Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            setResponse(response,"Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            setResponse(response,"JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
    }

    private void setResponse(HttpServletResponse response, String message){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods","*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        BaseResponseDtoMessageBuilder errorLoginDto = BaseResponseDto.messageBuilder().msg(DeniedCode.ACCESS_TOKEN_ERROR.code());
        errorLoginDto.dataMsg("errorSubject", message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String str = objectMapper.writeValueAsString(errorLoginDto.build());
            if (!response.getOutputStream().isReady()) {
                response.getOutputStream().write(str.getBytes());
            }
        } catch (IOException e){
            response.setStatus(404);
        }
    }
}
