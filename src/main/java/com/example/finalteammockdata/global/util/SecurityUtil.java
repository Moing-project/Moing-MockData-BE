package com.example.finalteammockdata.global.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class SecurityUtil {

    public static Optional<String> getCurrentUsername() {


        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String username = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails authenticationMember = (UserDetails) authentication.getPrincipal();
            username = authenticationMember.getUsername();
        }
        log.debug("getCurrentUsername, username: '{}'", username);

        return Optional.ofNullable(username);
    }
}