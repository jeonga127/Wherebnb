package com.example.wherebnb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionEnum {
    NOT_FOUND_ROOM(HttpStatus.NOT_FOUND, "404", "존재하지 않는 숙소입니다."),

    NOT_FOUND_CONDITION(HttpStatus.NOT_FOUND, "400", "유효하지지 않은 flexibleTripLength 값입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}