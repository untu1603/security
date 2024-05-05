package com.security.repository;

import com.security.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserLoginRepository extends JpaRepository<UserLogin, UUID> {
    Optional<UserLogin> findByUsername(String username);
}
