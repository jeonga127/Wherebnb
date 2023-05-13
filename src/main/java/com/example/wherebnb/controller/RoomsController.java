package com.example.wherebnb.controller;

import com.example.wherebnb.dto.RoomsRequestDto;
import com.example.wherebnb.service.RoomsService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RoomsController {

    private final RoomsService roomsService;

    // 숙소 등록
    @PostMapping("/room")
    public ResponseEntity roomInsert( @RequestBody @Valid RoomsRequestDto roomRequestDto){
        return roomsService.roomInsert(roomRequestDto);
    }

    // 숙소 수정
    @PutMapping("/room/{id}")
    public ResponseEntity roomUpdate(@PathVariable Long id, @RequestBody @Valid RoomsRequestDto roomRequestDto){
        return roomsService.roomUpdate(id, roomRequestDto);
    }

    // 숙소 삭제
    @DeleteMapping("/room/{id}")
    public ResponseEntity roomDelete(@PathVariable Long id){
        return roomsService.roomDelete(id);
    }

}
