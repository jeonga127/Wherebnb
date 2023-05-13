package com.example.wherebnb.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {
    private String username;
    private Long kakaoId;

    public UserInfoDto(String username, Long kakaoId){
        this.username = username;
        this.kakaoId = kakaoId;
    }
}
