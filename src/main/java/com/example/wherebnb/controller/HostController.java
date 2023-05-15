package com.example.wherebnb.controller;

import com.example.wherebnb.dto.HostRequestDto;
import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.security.UserDetailsImpl;
import com.example.wherebnb.service.HostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    // 숙소 전체 조회 (비회원)
    @GetMapping("/main")
    public ResponseDto getAllRooms(Pageable pageable){
        return hostService.getAllRooms(pageable);
    }

    // 숙소 전체 조회 (회원)
    @GetMapping("/user/main")
    public ResponseDto getUsersAllRooms(Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return hostService.getUsersAllRooms(pageable, userDetails.getUser());
    }

    // 숙소 상세 조회 (비회원)
    @GetMapping("/main/{id}")
    public ResponseDto getRoomDetail(@PathVariable Long id){
        return hostService.getRoomDetail(id);
    }

    // 숙소 키워드 검색 (비회원)
    @GetMapping("/main/keyword")
    public ResponseDto chooseSearch(String keyword1, String keyword2, Pageable pageable){
        return hostService.chooseSearch(keyword1, keyword2, pageable);
    }

    // 숙소 키워드 검색 (회원)
    @GetMapping("/user/keyword")
    public ResponseDto chooseSearch(String keyword1, String keyword2, @AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable){
        return hostService.chooseUsersSearch(keyword1,keyword2, userDetails.getUser(), pageable);
    }

    // 숙소 조건 검색 (비회원)
    @GetMapping("/main/condition")
    public ResponseDto getMainRoomBycondition(HostRequestDto hostreqeuestdto, Pageable pageable){
        return hostService.getRoomsByCondition(hostreqeuestdto, pageable);
    }

    // 숙소 조건 검색 (회원)
    @GetMapping("/user/condition")
    public ResponseDto getUserRoomBycondition(HostRequestDto hostreqeuestdto, @AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable) {
        return hostService.getRoomsByUserAndCondition(hostreqeuestdto, userDetails.getUser(), pageable);
    }
}
