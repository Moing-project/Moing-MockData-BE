package com.example.finalteammockdata.domain.auth.service;

import com.example.finalteammockdata.domain.auth.entity.AuthUser;
import com.example.finalteammockdata.domain.auth.exception.AuthDuplicationException;
import com.example.finalteammockdata.domain.auth.repository.AuthRepository;
import com.example.finalteammockdata.global.jwt.JwtUtil;
import com.example.finalteammockdata.global.redis.service.AuthenticationRedisService;
import com.example.finalteammockdata.global.redis.service.AuthenticationRedisService.AuthenticationDao;
import com.example.finalteammockdata.global.sercurity.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AuthServiceHelper {

    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;
    private final JavaMailSender javaMailSender;
    private final MailRedisService mailRedisService;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationRedisService authenticationRedisService;

    public AuthServiceHelper(JwtUtil jwtUtil, AuthRepository authRepository, JavaMailSender javaMailSender, MailRedisService mailRedisService, UserDetailsServiceImpl userDetailsService, AuthenticationRedisService authenticationRedisService) {
        this.jwtUtil = jwtUtil;
        this.authRepository = authRepository;
        this.javaMailSender = javaMailSender;
        this.mailRedisService = mailRedisService;
        this.userDetailsService = userDetailsService;
        this.authenticationRedisService = authenticationRedisService;
    }


    public void sendMail(String email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("account@moingtool.com","온라인협업툴 모잉!");

            // 1. 메일 수신자 설정
            mimeMessageHelper.setTo(email);
            int cunNel = (int) (Math.random() * 1000000);
            mailRedisService.setValueByExpireMinute(cunNel, email, 4L);
            // 2. 메일 제목 설정
            mimeMessage.setSubject(String.format("moing tool 인증번호 [%d]", cunNel));
            // 3. 메일 내용 설정
            mimeMessage.setText(String.format("moing tool 인증번호 [%d]", cunNel));
            // 4. 메일 전송
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    public String getEmailByCode(Integer code){
        return mailRedisService.getValueAndDelete(code);
    }

    public void sendMailToList(List<String> mailList) {
        log.info("Send mail for thread");
        try {
            List<MimeMessage> mimeMessages = new ArrayList<>();
            for (String s : mailList) {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage());
                mimeMessageHelper.setFrom("account@moingtool.com","온라인협업툴 모잉!");
                // 1. 메일 수신자 설정
                mimeMessageHelper.setTo(s);
                int cunNel = (int) (Math.random() * 900000) + 100000;
                while (!mailRedisService.SetValueAble(cunNel, s, Duration.ofMinutes(3L))){
                    cunNel = (int) (Math.random() * 900000) + 100000;
                };
                // 2. 메일 제목 설정
                mimeMessageHelper.setSubject(String.format("moing tool 인증번호 [%d]", cunNel));
                // 3. 메일 내용 설정
                mimeMessageHelper.setText(String.format("moing tool 인증번호 [%d]", cunNel));

                mimeMessages.add(mimeMessageHelper.getMimeMessage());
            }
            javaMailSender.send(mimeMessages.toArray(new MimeMessage[0]));
        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    /**
     *
     * @param request - getToken is not Null
     * @param response - return token for header
     * @return RefreshToken Allows true, false
     */
    public boolean returnAccessToken(HttpServletRequest request, HttpServletResponse response){
        String token = jwtUtil.getTokenFromRequestHeader(request, JwtUtil.REFRESH_TOKEN_HEADER);
        if(StringUtils.hasText(token)) {

            token = jwtUtil.substringToken(token);

            if(!jwtUtil.validateToken(token))
                return false;

            Claims claim = jwtUtil.getUserInfoFromToken(token);

            AuthenticationDao dao = authenticationRedisService.getValueANDExpired(claim.getSubject(), AuthenticationRedisService.AuthenticationTarget.TOKEN);

            if(dao.key() == null)
                return false;

            AuthUser user = authRepository.findByEmail(dao.key()).orElseThrow(() -> new AuthDuplicationException(404, "아이디를 찾을 수 없습니다."));

            if(dao.expired() < 600)
                userDetailsService.refreshToken(user.getEmail(), response);

            token = jwtUtil.createToken(user.getEmail(), user.getUserRole());
            jwtUtil.addJwtToHeader(token, response, JwtUtil.ACCESS_TOKEN_HEADER);
            return true;
        }
        return false;
    }
}
