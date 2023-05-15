package com.example.wherebnb.entity;

import com.example.wherebnb.dto.RoomsRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Rooms extends Timestamped{
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

    @Column(nullable = false)
    private int period;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();


    @Builder
    public Rooms(RoomsRequestDto roomsRequestDto, List<Image> images ,Users user) {
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
        //startDate, endDate 사이 기간 계산하고 int로 형변환
        this.period = (int) ChronoUnit.DAYS.between(roomsRequestDto.getStartDate(), roomsRequestDto.getEndDate());
        this.images = images;
        this.likesNum = 0;
    }

    // 수정
    public void roomUpdate(RoomsRequestDto roomsRequestDto, List<Image> newImages){
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
        //startDate, endDate 사이 기간 계산하고 int로 형변환
        this.period = (int) ChronoUnit.DAYS.between(roomsRequestDto.getStartDate(), roomsRequestDto.getEndDate());
        this.images = newImages;
    }

    public void updateLikes(boolean likeStatus) {
        this.likesNum = likeStatus ? likesNum + 1 : likesNum - 1;
    }
}
