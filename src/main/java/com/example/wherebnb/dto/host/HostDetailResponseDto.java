package com.example.wherebnb.dto.host;

import com.example.wherebnb.entity.ImageFile;
import com.example.wherebnb.entity.Rooms;
import com.example.wherebnb.entity.RoomsInfo;
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
    private String description;
    // 키워드 삽입 필요!
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
    private String roomName;
    private int likesNum;

    public HostDetailResponseDto toHostResponseDto(RoomsInfo roomsInfo) {
        return HostDetailResponseDto.builder()
                .roomId(roomsInfo.getRooms().getId())
                .imageFile(roomsInfo.getRooms().getImageFile())
                .roomName(roomsInfo.getRoomName())
                .description(roomsInfo.getDescription())
                .guestNum(roomsInfo.getGuestNum())
                .bedroomNum(roomsInfo.getBedroomNum())
                .bathroomNum(roomsInfo.getBathroomNum())
                .infantExist(roomsInfo.isInfantExist())
                .petExist(roomsInfo.isPetExist())
                .location(roomsInfo.getRooms().getLocation())
                .price(roomsInfo.getRooms().getPrice())
                .checkInDate(roomsInfo.getRooms().getCheckInDate())
                .checkOutDate(roomsInfo.getRooms().getCheckOutDate())
                .createdAt(roomsInfo.getRooms().getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")))
                .likesNum(roomsInfo.getLikesNum())
                .build();
    }
}
