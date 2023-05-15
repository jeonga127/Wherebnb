package com.example.wherebnb.controller;

import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.service.HostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    // 숙소 전체조회
    @GetMapping("/main")
    public ResponseDto getAllRooms(Pageable pageable){
        return hostService.getAllRooms(pageable);
    }


    //숙소 상세 조회
    @GetMapping("/main/{id}")
    public ResponseDto getRoomDetail(@PathVariable Long id){
        return hostService.getRoomDetail(id);
    }

    // 숙소 조건 검색

    // 숙소 키워드 검색
    @GetMapping("/main/keyword")
    public ResponseDto chosesearch(String keyword1, String keyword2){
        return hostService.chosesearch(keyword1, keyword2);
    }

    // 숙소 조건 검색
    @GetMapping("/main/condition")
    public ResponseDto getRoomBycondition(
            @RequestParam("startdate") String startDate,
            @RequestParam("enddate") String endDate,
            @RequestParam("adults") int adults,
            @RequestParam("children") int children,
            @RequestParam("infant") boolean infant,
            @RequestParam("pet") boolean pet,
            @RequestParam("flexible_trip_lengths") String flexibleTripLengths){
        return hostService.getRoomsByCondition(startDate, endDate, adults, children, infant, pet, flexibleTripLengths);
    }
}
