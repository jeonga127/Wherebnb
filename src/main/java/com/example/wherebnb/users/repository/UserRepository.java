package com.example.wherebnb.users.repository;

import com.example.wherebnb.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByKakaoId(Long kakaoId);

    Optional<Users> findByKakaoId(Long kakaoId);

    Optional<Users> findByUsername(String username);
}
