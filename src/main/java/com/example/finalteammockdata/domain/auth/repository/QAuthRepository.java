package com.example.finalteammockdata.domain.auth.repository;

public interface QAuthRepository {

    boolean findByEmailExist(String email);
    boolean findByNicknameExist(String nickname);

    String findByPasswordInUsername(String email);
}
