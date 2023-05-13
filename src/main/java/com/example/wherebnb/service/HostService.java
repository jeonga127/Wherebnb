package com.example.wherebnb.service;

import com.example.wherebnb.dto.HostResponseDto;
import com.example.wherebnb.entity.Rooms;
import com.example.wherebnb.repository.RoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HostService {
    private final RoomsRepository roomsRepository;



    // 숙소 전체 검색
    public List<HostResponseDto> getAllRooms() {
        List<Rooms> rooms = roomsRepository.findAll();
        List<HostResponseDto> hostResponseDtoList = new ArrayList<>();
        HostResponseDto hostResponseDto = new HostResponseDto();
        for (Rooms room : rooms) {
            hostResponseDtoList.add(hostResponseDto.toHostResponseDtoFullSearch(room));
        }
        return hostResponseDtoList;
    }

    // 숙소 상세조회
    public HostResponseDto getRoomDetail(Long id) {
        Rooms room = roomsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("존재하지않는 게시물"));
        HostResponseDto roomDetail = new HostResponseDto();
        return roomDetail.toHostResponseDto(room);
    }

    // 숙소조건검색


    // 숙소키워드검색


}
