package com.example.finalteammockdata.domain.auth.repository;

import com.example.finalteammockdata.domain.auth.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthUser, Long>, QAuthRepository {
}
