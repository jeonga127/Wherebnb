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

@RequestMapping("user/main")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final HostService hostService;

    @GetMapping
    public ResponseDto getUsersAllRooms(Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return hostService.getUsersAllRooms(pageable, userDetails.getUser());
    }

    @GetMapping("/keyword")
    public ResponseDto chooseSearch(String keyword1, String keyword2, @AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable){
        return hostService.chooseUsersSearch(keyword1,keyword2, userDetails.getUser(), pageable);
    }

    @GetMapping("/condition")
    public ResponseDto getUserRoomByCondition(HostRequestDto hostreqeuestdto, @AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable) {
        return hostService.getRoomsByUserAndCondition(hostreqeuestdto, userDetails.getUser(), pageable);
    }
}