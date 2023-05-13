package com.example.wherebnb.entity;

import com.example.wherebnb.dto.UserInfoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String kakaoId;

    @Column(nullable = false)
    private String username;
    public Users(UserInfoDto userInfoDto) {
        this.username = userInfoDto.getUsername();
        this.kakaoId = userInfoDto.getKakaoId();
    }
}
