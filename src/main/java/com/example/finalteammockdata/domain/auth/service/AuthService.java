package com.example.finalteammockdata.domain.auth.service;

import com.example.finalteammockdata.domain.auth.dto.AuthLoginRequestDto;
import com.example.finalteammockdata.domain.auth.dto.AuthSignupRequestDto;
import com.example.finalteammockdata.domain.auth.entity.AuthTempUser;
import com.example.finalteammockdata.domain.auth.entity.AuthUser;
import com.example.finalteammockdata.domain.auth.exception.AuthDuplicationException;
import com.example.finalteammockdata.domain.auth.repository.AuthRepository;
import com.example.finalteammockdata.domain.auth.repository.AuthTempRepository;
import com.example.finalteammockdata.global.aspect.annotation.TransactionalLockAround;
import com.example.finalteammockdata.global.dto.MessageResponseDto;
import com.example.finalteammockdata.global.enums.DeniedCode;
import com.example.finalteammockdata.global.exception.DeniedCodeException;
import com.example.finalteammockdata.global.exception.ErrorCodeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthServiceHelper authServiceHelper;
    private final AuthTempRepository authTempRepository;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder, AuthServiceHelper authServiceHelper, AuthTempRepository authTempRepository) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.authServiceHelper = authServiceHelper;
        this.authTempRepository = authTempRepository;
    }


    public MessageResponseDto signIn(AuthSignupRequestDto requestDto) {

        if(authRepository.findByEmailExist(requestDto.getEmail())){
            throw new AuthDuplicationException(409, "아이디가 중복되었습니다.");
        }
        if(authRepository.findByNicknameExist(requestDto.getNickname())){
            throw new AuthDuplicationException(409, "닉네임이 중복되었습니다.");
        }

        requestDto.encodePassword(passwordEncoder);

        AuthTempUser newUser = new AuthTempUser(requestDto);
        authTempRepository.save(newUser);

        MailSenderList.getInstance().addMailReceiver(newUser.getEmail());
        new Thread(() -> DeleteTempAccount(newUser.getEmail(), authTempRepository)).start();
        return MessageResponseDto.out(200,"do Interval Code");
    }

    public MessageResponseDto checkNickname(String nickname) {
        if(!StringUtils.hasText(nickname))
            throw new AuthDuplicationException(404, "닉네임을 적어주십시오.");
        if(authRepository.findByNicknameExist(nickname))
            throw new AuthDuplicationException(409, "닉네임이 중복되었습니다.");
        return MessageResponseDto.out(200,"success");
    }

    public MessageResponseDto checkUsername(String email) {
        if(!StringUtils.hasText(email))
            throw DeniedCodeException.out(DeniedCode.EMAIL_CORRECT_ERROR);
        if(authRepository.findByEmailExist(email))
            throw DeniedCodeException.out(DeniedCode.EMAIL_CORRECT_ERROR);
        return MessageResponseDto.out(200,"success");
    }

    @TransactionalLockAround
    public MessageResponseDto login(AuthLoginRequestDto requestDto) {
        String password = authRepository.findByPasswordInUsername(requestDto.email());
        if(password == null)
            throw new AuthDuplicationException(401, "아이디를 찾을 수 없습니다.");
        if(password.equals(requestDto.password()))
            return MessageResponseDto.out(200,"success");
        throw new AuthDuplicationException(401, "비밀번호가 틀립니다.");
    }

    public MessageResponseDto checkAuthCode(Integer code) {
        String email = authServiceHelper.getEmailByCode(code);
        if(email == null)
            throw new AuthDuplicationException(404, "Code Error");
        AuthTempUser authTempUser = authTempRepository.findByEmail(email).orElseThrow(() -> new AuthDuplicationException(404, "Time out"));
        AuthUser authUser = new AuthUser(authTempUser);
        authRepository.save(authUser);
        authTempRepository.delete(authTempUser);
        return MessageResponseDto.out(201,"create");
    }
    public MessageResponseDto returnAccessToken(HttpServletRequest request, HttpServletResponse response) {
        if(authServiceHelper.returnAccessToken(request,response)){
            return MessageResponseDto.out(200,"Success");
        }
        throw new AuthDuplicationException(404, "리프레시 토큰 오류");
    }

    private static void DeleteTempAccount(String email, AuthTempRepository authTempRepository){
        try {
            Thread.sleep(1000 * 60 * 4L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Delete Account = {}", email);
        authTempRepository.deleteByEmail(email);
    }
}
