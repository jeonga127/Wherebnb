package com.example.wherebnb.dto.host;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HostRequestDto {
    private String checkInDate;
    private String checkOutDate;
    private int adultsNum;
    private int childrenNum;
    private boolean infantExist;
    private boolean petExist;
    private String flexibleTripLengths;
}