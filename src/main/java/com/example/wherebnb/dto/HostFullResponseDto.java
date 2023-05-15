package com.example.wherebnb.dto;

import com.example.wherebnb.entity.Rooms;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HostFullResponseDto {
    private String imageUrl; // 이미지 url
    private String location; // 숙소위치
    private int price; // 가격
    private LocalDate startDate; // 시작날짜
    private LocalDate endDate; // 종료날짜
    private LocalDateTime createdAt; // 등록일
    private boolean likeStatus; // 좋아요 갯수

    public HostFullResponseDto (Rooms room) {
        this.imageUrl = "image_url";
        this.location = room.getLocation();
        this.price = room.getPrice();
        this.startDate = room.getStartDate();
        this.endDate = room.getEndDate();
        this.createdAt = room.getCreatedDate();
    }

    public HostFullResponseDto (Rooms room, boolean likeStatus) {
        this.imageUrl = "image_url";
        this.location = room.getLocation();
        this.price = room.getPrice();
        this.startDate = room.getStartDate();
        this.endDate = room.getEndDate();
        this.createdAt = room.getCreatedDate();
        this.likeStatus = likeStatus;
    }
}
