package com.security.repository;

import com.security.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
    @Query("select t from Token t join t.user u where u.userId = :id and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokenByUser(UUID id);

}
