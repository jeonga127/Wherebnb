package com.example.wherebnb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeywordEnum {
    BEST_VIEW("최고의 전망"),
    NEARBY_BEACH("해변 바로 앞"),
    CAMPING("캠핑장"),
    CITY_TRIP("도시"),
    LEISURE("레저"),
    RELAXING_MOOD("한적한 분위기");

    private String message;
    public static KeywordEnum toEnum(String message) {
        for(KeywordEnum keywordEnum : KeywordEnum.values()){
            if(keywordEnum.message.equals(message))
                return keywordEnum;
        }
        throw new IllegalArgumentException("Invalid Message : " + message);
    }
}
