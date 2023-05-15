package com.example.wherebnb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionEnum {
    NOT_FOUND_ROOM(HttpStatus.NOT_FOUND, "404", "존재하지 않는 숙소입니다."),

    NOT_FOUND_CONDITION(HttpStatus.NOT_FOUND, "400", "유효하지지 않은 flexibleTripLength 값입니다.");

    // 400 Bad Request
//    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "아이디 또는 비밀번호가 일치하지 않습니다."),
//    PASSWAORD_REGEX(HttpStatus.BAD_REQUEST, "400", "비밀번호는 8~15자리, a-z, A-Z, 숫자, 특수문자 조합으로 구성되어야 합니다."),

    // 401 Unauthorized
//    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "권한이 없습니다."),

    // 404 Not Found;
//    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "404_2", "댓글이 존재하지 않습니다."),
//    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "404_3", "회원이 존재하지 않습니다."),

    // 409 Conflict
//    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "409", "중복된 아이디가 이미 존재합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}