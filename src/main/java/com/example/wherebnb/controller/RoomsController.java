package com.example.wherebnb.controller;

import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.dto.RoomsRequestDto;
import com.example.wherebnb.dto.RoomsResponseDto;
import com.example.wherebnb.security.UserDetailsImpl;
import com.example.wherebnb.service.RoomsService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomsController {

    private final RoomsService roomsService;

    // 숙소 등록
    @PostMapping("/room")
    public ResponseDto roomInsert(@RequestParam(value = "images", required = false) List<MultipartFile> images, RoomsRequestDto roomRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{
        return roomsService.roomInsert(roomRequestDto, userDetails.getUser(), images);
    }

    // 숙소 수정
    @PutMapping("/room/{id}")
    public ResponseDto roomUpdate(@PathVariable("id") Long id , @RequestParam(value = "images", required = false) List<MultipartFile> images, RoomsRequestDto roomRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return roomsService.roomUpdate(id, roomRequestDto, userDetails.getUser(), images);
    }

    // 숙소 삭제
    @DeleteMapping("/room/{id}")
    public ResponseDto roomDelete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return roomsService.roomDelete(id, userDetails.getUser());
    }

    // 좋아요 등록
    @PutMapping("/like/{id}")
    public ResponseDto roomLikes(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return roomsService.roomLikes(id, userDetails.getUser());
    }

    @PostMapping("/test")
    public ResponseDto forTest(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return roomsService.forTest(userDetails.getUser());
    }
}
