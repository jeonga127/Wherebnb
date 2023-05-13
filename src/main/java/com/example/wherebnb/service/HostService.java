package com.example.wherebnb.service;

import com.example.wherebnb.dto.HostResponseDto;
import com.example.wherebnb.entity.Rooms;
import com.example.wherebnb.gobal.ApiException;
import com.example.wherebnb.gobal.ExceptionEnum;
import com.example.wherebnb.repository.RoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HostService {
    private final RoomsRepository roomsRepository;

    // 숙소 전체 검색
    public List<HostResponseDto> getAllRooms() {
        List<Rooms> rooms = roomsRepository.findAll();
        List<HostResponseDto> hostResponseDtoList = new ArrayList<>();
        HostResponseDto hostResponseDto = new HostResponseDto();
        for (Rooms room : rooms) {
            hostResponseDtoList.add(hostResponseDto.toHostResponseDtoFullSearch(room, "전체 조회 성공", HttpStatus.OK));
        }
        return hostResponseDtoList;
    }

    // 숙소 상세조회
    public HostResponseDto getRoomDetail(Long id) {
        Rooms room = roomsRepository.findById(id).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_POST));
        HostResponseDto roomDetail = new HostResponseDto();
        return roomDetail.toHostResponseDto(room, "조건 검색 성공", HttpStatus.OK);
    }


    public HostResponseDto chosesearch(String keyword1, String keyword2){
        List<Rooms> room =  roomsRepository.findAllByKeyword1OrKeyword2(keyword1, keyword2);
        return new HostResponseDto(room, "조건 검색 성공", HttpStatus.OK);
    }
}
