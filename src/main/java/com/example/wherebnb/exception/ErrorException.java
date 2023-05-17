package com.example.wherebnb.exception;

import com.example.wherebnb.config.global.SentrySupport;
import lombok.Getter;

@Getter
public class ErrorException extends RuntimeException {

    private final ExceptionEnum exceptionEnum;
    private final SentrySupport sentrySupport = new SentrySupport();


    public ErrorException (ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.exceptionEnum = exceptionEnum;
        sentrySupport.logSimpleMessage(exceptionEnum.getMessage());
    }
    public int getStatus(){
        return this.exceptionEnum.getStatus();
    }
}
