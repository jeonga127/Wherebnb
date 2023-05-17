package com.example.wherebnb.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDto {

    private int status;
    private String message;


    public ErrorResponseDto(ExceptionEnum exceptionEnum) {
        this.status = exceptionEnum.getStatus();
        this.message = exceptionEnum.getMessage();
    }
}