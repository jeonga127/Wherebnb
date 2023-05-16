package com.example.wherebnb.entity;

import com.example.wherebnb.dto.room.RoomsRequestDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Rooms extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String keyword1;

    @Column(nullable = false)
    private String keyword2;

    @Column(nullable = false)
    private int guestNum;

    @Column(nullable = false)
    private int bedroomNum;

    @Column(nullable = false)
    private int bathroomNum; // 욕실 갯수

    @Column(nullable = false)
    private boolean infantExist;

    @Column(nullable = false)
    private boolean petExist;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int likesNum;

    @Column(nullable = false)
    private int period;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @JsonManagedReference
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private List<ImageFile> imageFile;

    @Builder
    public Rooms(RoomsRequestDto roomsRequestDto, Users user) {
        this.roomName = roomsRequestDto.getRoomName();
        this.description = roomsRequestDto.getDescription();
        this.location = roomsRequestDto.getLocation();
        this.keyword1 = roomsRequestDto.getKeyword1();
        this.keyword2 = roomsRequestDto.getKeyword2();
        this.guestNum = roomsRequestDto.getGuestNum();
        this.bedroomNum = roomsRequestDto.getBedroomNum();
        this.bathroomNum = roomsRequestDto.getBathroomNum();
        this.infantExist = roomsRequestDto.isInfantExist();
        this.petExist = roomsRequestDto.isPetExist();
        this.checkInDate = LocalDate.parse(roomsRequestDto.getCheckInDate(), DateTimeFormatter.ISO_DATE);
        this.checkOutDate = LocalDate.parse(roomsRequestDto.getCheckOutDate(), DateTimeFormatter.ISO_DATE);;
        this.price = roomsRequestDto.getPrice();
        this.user = user;
        this.period = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        this.likesNum = 0;
    }

    // 수정
    public void roomUpdate(RoomsRequestDto roomsRequestDto){
        this.roomName = roomsRequestDto.getRoomName();
        this.description = roomsRequestDto.getDescription();
        this.location = roomsRequestDto.getLocation();
        this.keyword1 = roomsRequestDto.getKeyword1();
        this.keyword2 = roomsRequestDto.getKeyword2();
        this.guestNum = roomsRequestDto.getGuestNum();
        this.bedroomNum = roomsRequestDto.getBedroomNum();
        this.bathroomNum = roomsRequestDto.getBathroomNum();
        this.infantExist = roomsRequestDto.isInfantExist();
        this.petExist = roomsRequestDto.isPetExist();
        this.checkInDate = LocalDate.parse(roomsRequestDto.getCheckInDate(), DateTimeFormatter.ISO_DATE);
        this.checkOutDate = LocalDate.parse(roomsRequestDto.getCheckOutDate(), DateTimeFormatter.ISO_DATE);;
        this.price = roomsRequestDto.getPrice();
        this.period = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    public void setImageFile(List<ImageFile> imageFile){
        this.imageFile = imageFile;
    }

    public void updateLikes(boolean likeStatus) {
        this.likesNum = likeStatus ? likesNum + 1 : likesNum - 1;
    }
}
