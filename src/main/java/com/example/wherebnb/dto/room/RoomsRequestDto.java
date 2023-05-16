package com.example.wherebnb.dto.room;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NonNull
public class RoomsRequestDto {
    private String roomName; // 숙소이름
    private String description; // 숙소 설명
    private String location; // 숙소 위치
    private String keyword1; // 키워드1
    private String keyword2; // 키워드2
    private int guestNum; // 수용 인원
    private int bedroomNum; // 침실 갯수
    private int bathroomNum; // 욕실 갯수
    private boolean infant; //유아 동반가능여부
    private boolean pet; //애견 동반 가능여부
    private LocalDate checkInDate; //시작 날짜
    private LocalDate checkOutDate; // 종료 날짜
    private int price; //요금
}