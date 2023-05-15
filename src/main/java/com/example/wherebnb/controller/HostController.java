package com.example.wherebnb.controller;

import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.security.UserDetailsImpl;
import com.example.wherebnb.service.HostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

    // 숙소 키워드 검색
    @GetMapping("/main/keyword")
    public ResponseDto chooseSearch(String keyword1, String keyword2){
        return hostService.chooseSearch(keyword1, keyword2);
    }

    @GetMapping("/user/keyword")
    public ResponseDto chooseSearch(String keyword1, String keyword2, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return hostService.chooseUsersSearch(keyword1,keyword2, userDetails.getUser());
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

    @GetMapping("/user/condition")
    public ResponseDto getRoomBycondition(
            @RequestParam("startdate") String startDate,
            @RequestParam("enddate") String endDate,
            @RequestParam("adults") int adults,
            @RequestParam("children") int children,
            @RequestParam("infant") boolean infant,
            @RequestParam("pet") boolean pet,
            @RequestParam("flexible_trip_lengths") String flexibleTripLengths,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return hostService.getRoomsByUserAndCondition(startDate, endDate, adults, children, infant, pet, flexibleTripLengths, userDetails.getUser());
    }
}

