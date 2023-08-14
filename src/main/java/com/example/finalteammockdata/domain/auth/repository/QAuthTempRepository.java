package com.example.finalteammockdata.domain.auth.repository;

import com.example.finalteammockdata.domain.auth.entity.AuthTempUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QAuthTempRepository {
    Long deleteByEmail(String email);
}
