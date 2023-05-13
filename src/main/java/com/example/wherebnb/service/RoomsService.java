package com.example.wherebnb.service;

import com.example.wherebnb.dto.RoomsRequestDto;
import com.example.wherebnb.entity.Rooms;
import com.example.wherebnb.gobal.ApiException;
import com.example.wherebnb.gobal.ExceptionEnum;
import com.example.wherebnb.repository.RoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomsService {

    private final RoomsRepository roomsrepository;

    // 숙소 등록
    public ResponseEntity roomInsert(RoomsRequestDto roomsRequestDto){
        roomsrepository.saveAndFlush(new Rooms(roomsRequestDto));
        //로그인여부 확인 로직 추가

        return new ResponseEntity(HttpStatus.OK);
    }



    // 숙소 수정
    public ResponseEntity roomUpdate(Long id,RoomsRequestDto roomsRequestDto){
        Rooms rooms = roomsrepository.findById(id).orElseThrow(  // 수정할 게시글 있는지 확인
                () -> new ApiException(ExceptionEnum.NOT_FOUND_POST)
        );

        //로그인여부 확인 로직 추가

        rooms.Roomupdate(roomsRequestDto);

        return new ResponseEntity(HttpStatus.OK);
    }
    
    
    
    // 숙소 삭제
    public ResponseEntity roomDelete(Long id){
        roomsrepository.findById(id).orElseThrow( // 삭제할 게시글 있는지 확인
                () -> new ApiException(ExceptionEnum.NOT_FOUND_POST)
        );

        //로그인여부 확인 로직 추가

        roomsrepository.deleteById(id);

        return new ResponseEntity(HttpStatus.OK);
    }

}
