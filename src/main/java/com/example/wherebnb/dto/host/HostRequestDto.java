package com.example.wherebnb.dto.host;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HostRequestDto {
    private String checkInDate; // 체크인 날짜
    private String checkOutDate;// 체크아웃 날짜
    private int adultsNum; // 성인숫자
    private int childrenNum; // 어린이숫자
    private boolean infantExist; // 유아 동반 여부
    private boolean petExist; // 반려 동물 동반 여부
    private String flexibleTripLengths; // 유여한 일정
}