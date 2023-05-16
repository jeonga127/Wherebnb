package com.example.wherebnb.dto.host;

import com.example.wherebnb.entity.ImageFile;
import com.example.wherebnb.entity.Rooms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostDetailResponseDto {
    private Long roomId;
    private List<ImageFile> imageFile;
    private String roomName;
    private String description;
    private String keyword1;
    private String keyword2;
    private int guestNum;
    private int bedroomNum;
    private int bedNum;
    private int bathroomNum;
    private boolean infantExist;
    private boolean petExist;
    private String location;
    private int price;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String createdAt;
    private int likesNum;

    public HostDetailResponseDto toHostResponseDto(Rooms room) {
        return HostDetailResponseDto.builder()
                .roomId(room.getId())
                .imageFile(room.getImageFile())
                .roomName(room.getRoomName())
                .description(room.getDescription())
                .keyword1(room.getKeyword1())
                .keyword2(room.getKeyword2())
                .guestNum(room.getGuestNum())
                .bedroomNum(room.getBedroomNum())
                .bathroomNum(room.getBathroomNum())
                .infantExist(room.isInfantExist())
                .petExist(room.isPetExist())
                .location(room.getLocation())
                .price(room.getPrice())
                .checkInDate(room.getCheckInDate())
                .checkOutDate(room.getCheckOutDate())
                .createdAt(room.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")))
                .likesNum(room.getLikesNum())
                .build();
    }
}
