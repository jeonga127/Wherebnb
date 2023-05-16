package com.example.wherebnb.entity;

import com.example.wherebnb.dto.room.RoomsRequestDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private int bathrooomNum;

    @Column(nullable = false)
    private boolean infant;

    @Column(nullable = false)
    private boolean pet;

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
        this.bathrooomNum = roomsRequestDto.getBathrooomNum();
        this.infant = roomsRequestDto.isInfant();
        this.pet = roomsRequestDto.isPet();
        this.checkInDate = roomsRequestDto.getCheckInDate();
        this.checkOutDate = roomsRequestDto.getCheckOutDate();
        this.price = roomsRequestDto.getPrice();
        this.user = user;
        this.period = (int) ChronoUnit.DAYS.between(roomsRequestDto.getCheckInDate(), roomsRequestDto.getCheckOutDate());
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
        this.bathrooomNum = roomsRequestDto.getBathrooomNum();
        this.infant = roomsRequestDto.isInfant();
        this.pet = roomsRequestDto.isPet();
        this.checkInDate = roomsRequestDto.getCheckInDate();
        this.checkOutDate = roomsRequestDto.getCheckOutDate();
        this.price = roomsRequestDto.getPrice();
        this.period = (int) ChronoUnit.DAYS.between(roomsRequestDto.getCheckInDate(), roomsRequestDto.getCheckOutDate());
    }

    public void setImageFile(List<ImageFile> imageFile){
        this.imageFile = imageFile;
    }

    public void updateLikes(boolean likeStatus) {
        this.likesNum = likeStatus ? likesNum + 1 : likesNum - 1;
    }
}
