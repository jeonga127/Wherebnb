package com.example.wherebnb.controller;

import com.example.wherebnb.dto.HostResponseDto;
import com.example.wherebnb.service.HostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    // 숙소 전체조회
    @GetMapping("/main")
    public List<HostResponseDto> getAllRooms(){
        return hostService.getAllRooms();
    }


    //숙소 상세 조회
    @GetMapping("/main/{id}")
    public HostResponseDto getRoomDetail(@PathVariable Long id){
        return hostService.getRoomDetail(id);
    }

    // 숙소 조건 검색

    // 숙소 키워드 검색
    @GetMapping("/main/keyword")
    public HostResponseDto chosesearch(String keyword1, String keyword2){
        return hostService.chosesearch(keyword1, keyword2);
    }


}
