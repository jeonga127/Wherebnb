package com.example.wherebnb.controller;

import com.example.wherebnb.dto.host.HostRequestDto;
import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.security.UserDetailsImpl;
import com.example.wherebnb.service.HostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final HostService hostService;

    // 숙소 전체 조회 (회원)
    @GetMapping
    public ResponseDto getUsersAllRooms(Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return hostService.getUsersAllRooms(pageable, userDetails.getUser());
    }

    // 숙소 키워드 검색 (회원)
    @GetMapping("/keyword")
    public ResponseDto chooseSearch(String keyword1, String keyword2, @AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable){
        return hostService.chooseUsersSearch(keyword1,keyword2, userDetails.getUser(), pageable);
    }

    // 숙소 조건 검색 (회원)
    @GetMapping("/condition")
    public ResponseDto getUserRoomBycondition(HostRequestDto hostreqeuestdto, @AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable) {
        return hostService.getRoomsByUserAndCondition(hostreqeuestdto, userDetails.getUser(), pageable);
    }
}
