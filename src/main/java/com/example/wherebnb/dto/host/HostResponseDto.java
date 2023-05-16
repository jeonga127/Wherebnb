package com.example.wherebnb.dto.host;

import com.example.wherebnb.entity.ImageFile;
import com.example.wherebnb.entity.Rooms;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class HostResponseDto {
    private Long roomId;
    private List<ImageFile> imageFile;
    private String location;
    private int price;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private boolean likeStatus;

    public HostResponseDto(Rooms room) {
        this.roomId = room.getId();
        this.imageFile = room.getImageFile();
        this.location = room.getLocation();
        this.price = room.getPrice();
        this.checkInDate = room.getCheckInDate();
        this.checkOutDate = room.getCheckOutDate();
        this.createdAt = room.getCreatedAt();
        this.likeStatus = false;
    }

    public HostResponseDto(Rooms room, boolean likeStatus) {
        this.roomId = room.getId();
        this.imageFile = room.getImageFile();
        this.location = room.getLocation();
        this.price = room.getPrice();
        this.checkInDate = room.getCheckInDate();
        this.checkOutDate = room.getCheckOutDate();
        this.createdAt = room.getCreatedAt();
        this.likeStatus = likeStatus;
    }
}
