package com.example.wherebnb.service;

import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.dto.RoomsRequestDto;
import com.example.wherebnb.dto.RoomsResponseDto;
import com.example.wherebnb.entity.Rooms;
import com.example.wherebnb.entity.Users;
import com.example.wherebnb.global.ApiException;
import com.example.wherebnb.global.ExceptionEnum;
import com.example.wherebnb.repository.RoomsRepository;
import com.example.wherebnb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomsService {

    private final RoomsRepository roomsRepository;

    // 숙소 등록
    public ResponseDto roomInsert(RoomsRequestDto roomsRequestDto, Users user) {
        roomsRepository.save(new Rooms(roomsRequestDto, user));
        return ResponseDto.setSuccess("숙소 등록을 완료하였습니다.", null);
    }

    // 숙소 수정
    public ResponseDto roomUpdate(Long id, RoomsRequestDto roomsRequestDto, Users user) {
        Rooms room = roomsRepository.findById(id).orElseThrow(  // 수정할 게시글 있는지 확인
                () -> new ApiException(ExceptionEnum.NOT_FOUND_ROOM));
        room.roomUpdate(roomsRequestDto);
        return ResponseDto.setSuccess("숙소 수정을 완료하였습니다.", null);
    }


    // 숙소 삭제
    public ResponseDto roomDelete(Long id, Users users) {
        Rooms room = roomsRepository.findById(id).orElseThrow( // 삭제할 게시글 있는지 확인
                () -> new ApiException(ExceptionEnum.NOT_FOUND_ROOM));

       roomsRepository.delete(room);
        return ResponseDto.setSuccess("숙소 삭제를 완료하였습니다.", null);
    }
}
