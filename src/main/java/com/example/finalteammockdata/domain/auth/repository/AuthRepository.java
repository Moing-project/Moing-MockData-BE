package com.example.finalteammockdata.domain.auth.repository;

import com.example.finalteammockdata.domain.auth.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<AuthUser, Long>, QAuthRepository {
    Optional<AuthUser> findByEmail(String email);
}
