package com.example.wherebnb.dto.host;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HostRequestDto {
    private LocalDate startDate; // 체크인 날짜
    private LocalDate endDate;// 체크아웃 날짜
    private int adults; // 성인숫자
    private int children; // 어린이숫자
    private boolean infant; // 유아 동반 여부
    private boolean pet; // 반려 동물 동반 여부
    private String flexibleTripLengths; // 유여한 일정
}