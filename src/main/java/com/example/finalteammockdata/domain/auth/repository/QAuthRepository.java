package com.example.finalteammockdata.domain.auth.repository;

import com.example.finalteammockdata.domain.auth.dto.AuthWorkSoloResponseDto;

public interface QAuthRepository {

    boolean findByEmailExist(String email);
    boolean findByNicknameExist(String nickname);

    String findByPasswordInUsername(String email);

    AuthWorkSoloResponseDto findByProfileImageAndNicknameById(Long id);
}
