package com.example.wherebnb.service;

import com.example.wherebnb.dto.RoomsRequestDto;
import com.example.wherebnb.dto.RoomsResponseDto;
import com.example.wherebnb.entity.Rooms;
import com.example.wherebnb.entity.Users;
import com.example.wherebnb.gobal.ApiException;
import com.example.wherebnb.gobal.ExceptionEnum;
import com.example.wherebnb.repository.RoomsRepository;
import com.example.wherebnb.repository.UserRepository;
import com.example.wherebnb.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomsService {

    private final RoomsRepository roomsrepository;

    private final UserRepository userRepository;

    // 숙소 등록
    public RoomsResponseDto roomInsert(RoomsRequestDto roomsRequestDto, Users users) {
        //로그인여부 확인 로직
        if (logincheck(null, users)) {
            roomsrepository.saveAndFlush(new Rooms(roomsRequestDto, users.getKakaoId()));
        }
        return new RoomsResponseDto("숙소등록을 완료하였습니다", HttpStatus.OK);
    }

    // 숙소 수정
    public RoomsResponseDto roomUpdate(Long id, RoomsRequestDto roomsRequestDto, Users users) {
        Rooms rooms = roomsrepository.findById(id).orElseThrow(  // 수정할 게시글 있는지 확인
                () -> new ApiException(ExceptionEnum.NOT_FOUND_POST));

        //로그인여부 확인 로직
        if (logincheck(rooms.getRoomsid(), users)) {
            rooms.Roomupdate(roomsRequestDto);
            return new RoomsResponseDto("숙소수정을 완료하였습니다", HttpStatus.OK);
        }
        return new RoomsResponseDto("숙소수정을 실패하였습니다", HttpStatus.FOUND);
      
    }


    // 숙소 삭제
    public RoomsResponseDto roomDelete(Long id, Users users) {
        Rooms rooms = roomsrepository.findById(id).orElseThrow( // 삭제할 게시글 있는지 확인
                () -> new ApiException(ExceptionEnum.NOT_FOUND_POST));

        //로그인여부 확인 로직
        if (logincheck(rooms.getRoomsid(), users)) {
            roomsrepository.deleteById(id);
        }

        return new RoomsResponseDto("숙소삭제를 완료하였습니다", HttpStatus.OK);
    }

    // 로그인여부 확인 메소드
    public boolean logincheck(String userid,Users users) {
        // 등록시 확인
        if (userid == null) {
            Users users1 = userRepository.findByKakaoId(users.getKakaoId()).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_POST));
            if (users1.getKakaoId() != null) {
                return true;
            }
            return false;
        }
        // 수정 삭제시 확인
        if (userid != null && users.getKakaoId().equals(userid)) {
            return true;
        }
        return false;
    }
}
