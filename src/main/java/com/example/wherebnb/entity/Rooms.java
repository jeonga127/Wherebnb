package com.example.wherebnb.entity;

import com.example.wherebnb.dto.room.RoomsRequestDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Rooms extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @JsonManagedReference
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private List<ImageFile> imageFile;

    @Builder
    public Rooms(RoomsRequestDto roomsRequestDto, Users user) {
        this.location = roomsRequestDto.getLocation();
        this.price = roomsRequestDto.getPrice();
        this.checkInDate = LocalDate.parse(roomsRequestDto.getCheckInDate(), DateTimeFormatter.ISO_DATE);
        this.checkOutDate = LocalDate.parse(roomsRequestDto.getCheckOutDate(), DateTimeFormatter.ISO_DATE);
        this.user = user;
    }

    public void roomsUpdate(RoomsRequestDto roomsRequestDto) {
       this.location = roomsRequestDto.getLocation();
       this.price = roomsRequestDto.getPrice();
       this.checkInDate = LocalDate.parse(roomsRequestDto.getCheckInDate(), DateTimeFormatter.ISO_DATE);
       this.checkOutDate = LocalDate.parse(roomsRequestDto.getCheckOutDate(), DateTimeFormatter.ISO_DATE);
    }

    public void setImageFile(List<ImageFile> imageFile){
        this.imageFile = imageFile;
    }
}
