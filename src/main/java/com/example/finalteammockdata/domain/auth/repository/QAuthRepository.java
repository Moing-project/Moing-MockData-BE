package com.example.finalteammockdata.domain.auth.repository;

public interface QAuthRepository {

    boolean findByUsernameExist(String username);
    boolean findByNicknameExist(String nickname);

    String findByPasswordInUsername(String username);
}
