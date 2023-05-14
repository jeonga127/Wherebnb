package com.example.wherebnb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomsResponseDto {
    private String message;
    private HttpStatus httpStatus;
}
