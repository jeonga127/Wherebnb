package com.example.wherebnb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionEnum {

    CONDITION_NOT_FOUND("조건을 다시 설정해주세요.", HttpStatus.NOT_FOUND.value()),
    NOT_ALLOWED_AUTHORIZATIONS("작성자만 수정,삭제 할 수 있습니다.", HttpStatus.FORBIDDEN.value()),
    ROOM_NOT_FOUND("존재하지 않는 숙소입니다.", HttpStatus.NOT_FOUND.value()),


    INTERNAL_SERVER_ERROR("서버 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    UNKNOWN_ERROR("알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());

    private final int status;
    private final String message;


    ExceptionEnum(String message, int status) {
        this.status = status;
        this.message = message;
    }
}