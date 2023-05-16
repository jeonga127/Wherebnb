package com.example.wherebnb.entity;

import com.example.wherebnb.dto.room.RoomsRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class RoomsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int guestNum;

    @Column(nullable = false)
    private int bedroomNum;

    @Column(nullable = false)
    private int bathroomNum;

    @Column(nullable = false)
    private boolean infantExist;

    @Column(nullable = false)
    private boolean petExist;

    @Column(nullable = false)
    private int likesNum;

    @Column(nullable = false)
    private int period;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rooms_id")
    private Rooms rooms;

    @Builder
    public RoomsInfo(RoomsRequestDto roomsRequestDto, Rooms rooms) {
        this.roomName = roomsRequestDto.getRoomName();
        this.description = roomsRequestDto.getDescription();
        this.guestNum = roomsRequestDto.getGuestNum();
        this.bedroomNum = roomsRequestDto.getBedroomNum();
        this.bathroomNum = roomsRequestDto.getBathroomNum();
        this.infantExist = roomsRequestDto.isInfantExist();
        this.petExist = roomsRequestDto.isPetExist();
        this.rooms = rooms;
        this.likesNum = 0;
    }

    public void roomsInfoUpdate(RoomsRequestDto roomsRequestDto) {
        this.roomName = roomsRequestDto.getRoomName();
        this.description = roomsRequestDto.getDescription();
        this.guestNum = roomsRequestDto.getGuestNum();
        this.bedroomNum = roomsRequestDto.getBedroomNum();
        this.bathroomNum = roomsRequestDto.getBathroomNum();
        this.infantExist = roomsRequestDto.isInfantExist();
        this.petExist = roomsRequestDto.isPetExist();
    }

    public void setPeriod(LocalDate checkInDate, LocalDate checkOutDate){
        this.period = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
    public void updateLikes(boolean likeStatus) {
        this.likesNum = likeStatus ? likesNum + 1 : likesNum - 1;
    }
}
