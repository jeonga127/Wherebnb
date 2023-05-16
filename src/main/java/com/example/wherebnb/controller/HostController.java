package com.example.wherebnb.controller;

import com.example.wherebnb.dto.host.HostRequestDto;
import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.service.HostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("main")
@RestController
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    @GetMapping
    public ResponseDto getAllRooms(Pageable pageable){
        return hostService.getAllRooms(pageable);
    }

    @GetMapping("/{id}")
    public ResponseDto getRoomDetail(@PathVariable Long id){
        return hostService.getRoomDetail(id);
    }

    @GetMapping("/keyword")
    public ResponseDto chooseSearch(String keyword1, String keyword2, Pageable pageable){
        return hostService.chooseSearch(keyword1, keyword2, pageable);
    }

    @GetMapping("/condition")
    public ResponseDto getMainRoomByCondition(HostRequestDto hostreqeuestdto, Pageable pageable){
        return hostService.getRoomsByCondition(hostreqeuestdto, pageable);
    }
}
