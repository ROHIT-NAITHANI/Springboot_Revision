package com.example.springDataDemo.repository;

import com.example.springDataDemo.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
   Optional<RefreshToken> findByTokenHash(String token);
}
