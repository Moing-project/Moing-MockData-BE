package com.example.finalteammockdata.domain.auth.repository;

import com.example.finalteammockdata.domain.auth.dao.AuthNickAndImageDao;
import com.example.finalteammockdata.domain.auth.dto.AuthWorkSoloResponseDto;

public interface QAuthRepository {

    boolean findByEmailExist(String email);
    boolean findByNicknameExist(String nickname);

    String findByPasswordInUsername(String email);

    AuthNickAndImageDao findByProfileImageAndNicknameById(Long id);
}
