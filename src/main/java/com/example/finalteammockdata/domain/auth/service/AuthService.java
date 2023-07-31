package com.example.finalteammockdata.domain.auth.service;

import com.example.finalteammockdata.domain.auth.dto.AuthLoginRequestDto;
import com.example.finalteammockdata.domain.auth.dto.AuthSignupRequestDto;
import com.example.finalteammockdata.domain.auth.entity.AuthUser;
import com.example.finalteammockdata.domain.auth.exception.AuthDuplicationException;
import com.example.finalteammockdata.domain.auth.repository.AuthRepository;
import com.example.finalteammockdata.global.dto.BaseResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class AuthService {

    private final AuthRepository authRepository;

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
}
