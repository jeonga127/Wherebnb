package com.example.wherebnb.service;

import com.example.wherebnb.dto.HostResponseDto;
import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.entity.Rooms;
import com.example.wherebnb.global.ApiException;
import com.example.wherebnb.global.ExceptionEnum;
import com.example.wherebnb.repository.RoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.wherebnb.dto.HostFullResponseDto;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HostService {
    private final RoomsRepository roomsRepository;

    // 숙소 전체 검색
    public ResponseDto getAllRooms(Pageable pageable) {
        List<HostFullResponseDto> roomList = roomsRepository.findAll(pageable).getContent()
                .stream().map(HostFullResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("전체 조회 성공", roomList);
    }

    // 숙소 상세조회
    public ResponseDto getRoomDetail(Long id) {
        Rooms room = roomsRepository.findById(id).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ROOM));
        return ResponseDto.setSuccess("상세 조회 성공", room);
    }

    // 숙소 조건 검색
    public ResponseDto getRoomByCondition(){
        return ResponseDto.setSuccess("조건 검색 성공", null);
    }

    // 숙소 키워드 검색
    public ResponseDto chosesearch(String keyword1, String keyword2){
        List<Rooms> room =  roomsRepository.findAllByKeyword1OrKeyword2(keyword1, keyword2);
        return ResponseDto.setSuccess("키워드 검색 성공", room);
    }
}
