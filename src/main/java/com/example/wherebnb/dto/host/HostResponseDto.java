package com.example.wherebnb.dto.host;

import com.example.wherebnb.entity.Rooms;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HostResponseDto {
    private String imageUrl; // 이미지 url
    private String location; // 숙소위치
    private int price; // 가격
    private LocalDate checkInDate; // 시작날짜
    private LocalDate checkOutDate; // 종료날짜
    private LocalDateTime createdAt; // 등록일
    private boolean likeStatus; // 좋아요 갯수

    public HostResponseDto(Rooms room) {
        this.imageUrl = "image_url";
        this.location = room.getLocation();
        this.price = room.getPrice();
        this.checkInDate = room.getCheckInDate();
        this.checkOutDate = room.getCheckOutDate();
        this.createdAt = room.getCreatedAt();
        this.likeStatus = false;
    }

    public HostResponseDto(Rooms room, boolean likeStatus) {
        this.imageUrl = "image_url";
        this.location = room.getLocation();
        this.price = room.getPrice();
        this.checkInDate = room.getCheckInDate();
        this.checkOutDate = room.getCheckOutDate();
        this.createdAt = room.getCreatedAt();
        this.likeStatus = likeStatus;
    }
}