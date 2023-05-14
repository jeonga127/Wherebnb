package com.example.wherebnb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HostRequestDto {
    private LocalDateTime startDate; // 등록날짜
    private int adults; // 성인숫자
    private int children; // 어린이숫자
    private boolean infant; // 유아 동반 여부
    private boolean pet; // 반려 동물 동반 여부
    private String flexibleTripLengths; // 유여한 일정
}
