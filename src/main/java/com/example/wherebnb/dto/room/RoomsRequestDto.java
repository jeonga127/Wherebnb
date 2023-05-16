package com.example.wherebnb.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomsRequestDto {
    private String roomName;
    private String description;
    private int guestNum;
    private int bedroomNum;
    private int bathroomNum;
    private boolean infantExist;
    private boolean petExist;

    private String location;
    private int price;
    private String checkInDate;
    private String checkOutDate;

    private String keyword1;
    private String keyword2;
}
