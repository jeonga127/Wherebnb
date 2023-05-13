package com.example.wherebnb.controller;

import com.example.wherebnb.dto.RoomsRequestDto;
import com.example.wherebnb.dto.RoomsResponseDto;
import com.example.wherebnb.security.UserDetailsImpl;
import com.example.wherebnb.service.RoomsService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RoomsController {

    private final RoomsService roomsService;

    // 숙소 등록
    @PostMapping("/room")
    public RoomsResponseDto roomInsert(@RequestBody @Valid RoomsRequestDto roomRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return roomsService.roomInsert(roomRequestDto, userDetails.getUser());
    }

    // 숙소 수정
    @PutMapping("/room/{id}")
    public RoomsResponseDto roomUpdate(@PathVariable Long id, @RequestBody @Valid RoomsRequestDto roomRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return roomsService.roomUpdate(id, roomRequestDto, userDetails.getUser());
    }

    // 숙소 삭제
    @DeleteMapping("/room/{id}")
    public RoomsResponseDto roomDelete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return roomsService.roomDelete(id, userDetails.getUser());
    }

}
