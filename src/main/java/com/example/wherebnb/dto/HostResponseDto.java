package com.example.wherebnb.dto;

import com.example.wherebnb.entity.Rooms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostResponseDto {
    private String imageurl; // 이미지 url
    private String roomName; //숙소이름
    private String description; // 숙소설명
    private String keyword1; // 키워드1
    private String keyword2; // 키워드2
    private int guestNum; // 수용 인원
    private int bedroomNum; // 침실 갯수
    private int bedNum; // 침대 갯수
    private int bathrooomNum; //욕실 갯수
    private boolean infant; // 유아 동반 가능 여부
    private boolean pet; // 애견 동반 가능 여부
    private String location; // 숙소위치
    private int price; // 가격
    private LocalDateTime startDate; // 시작날짜
    private LocalDateTime endDate; // 종료날짜
    private LocalDateTime createdAt; // 등록일


    public HostResponseDto toHostResponseDtoFullSearch(Rooms room) {
        return HostResponseDto.builder()
                .imageurl("image_url")
                .location(room.getLocation())
                .price(room.getPrice())
                .startDate(room.getStartDate())
                .endDate(room.getEndDate())
                .createdAt(room.getCreatedDate())
                .build();
    }

    public HostResponseDto toHostResponseDto(Rooms room) {
        return HostResponseDto.builder()
                .imageurl("imageurl")
                .roomName(room.getRoomName())
                .description(room.getDescription())
                .keyword1(room.getKeyword1())
                .keyword2(room.getKeyword2())
                .guestNum(room.getGuestNum())
                .bedroomNum(room.getBedroomNum())
                .bathrooomNum(room.getBathrooomNum())
                .infant(room.isInfant())
                .pet(room.isPet())
                .location(room.getLocation())
                .price(room.getPrice())
                .startDate(room.getStartDate())
                .endDate(room.getEndDate())
                .createdAt(room.getCreatedDate())
                .build();
    }



}
