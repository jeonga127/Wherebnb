package com.example.wherebnb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {
    private String username;
    private String kakaoId;

    public UserInfoDto(String username, String kakaoId){
        this.username = username;
        this.kakaoId = kakaoId;
    }
}
