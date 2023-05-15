package com.example.wherebnb.entity;

import com.example.wherebnb.dto.RoomsRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Rooms extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomName; // 숙소 이름

    @Column(nullable = false)
    private String description; // 숙소 설명

    @Column(nullable = false)
    private String location; // 숙소 위치

    @Column(nullable = false)
    private String keyword1; // 키워드1

    @Column(nullable = false)
    private String keyword2; // 키워드2

    @Column(nullable = false)
    private int guestNum; //수용 인원

    @Column(nullable = false)
    private int bedroomNum; // 침실 갯수

    @Column(nullable = false)
    private int bathrooomNum; // 욕실 갯수

    @Column(nullable = false)
    private boolean infant; // 유아 동반 가능 여부

    @Column(nullable = false)
    private boolean pet; // 애견 동반 가능 여부

    @Column(nullable = false)
    private LocalDate startDate; // 시작 날짜

    @Column(nullable = false)
    private LocalDate endDate; // 종료 날짜

    @Column(nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int likesNum;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;


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
        this.startDate = roomsRequestDto.getStartDate();
        this.endDate = roomsRequestDto.getEndDate();
        this.price = roomsRequestDto.getPrice();
        this.user = user;
        this.likesNum = 0;
    }

    // 수정
    public void roomUpdate(RoomsRequestDto roomsRequestDto) {
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
        this.startDate = roomsRequestDto.getStartDate();
        this.endDate = roomsRequestDto.getEndDate();
        this.price = roomsRequestDto.getPrice();
    }

    public void updateLikes(boolean likeStatus) {
        this.likesNum = likeStatus ? likesNum++ : likesNum--;
    }
}
