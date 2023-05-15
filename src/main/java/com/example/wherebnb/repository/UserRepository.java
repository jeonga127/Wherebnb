package com.example.wherebnb.repository;

import com.example.wherebnb.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByKakaoId(String kakaoId);
    Optional<Users> findByKakaoId(String kakaoId);
    Optional<Users> findByUsername(String username);
}
