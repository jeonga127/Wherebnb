package com.example.wherebnb.service;

import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.dto.RoomsRequestDto;
import com.example.wherebnb.entity.Likes;
import com.example.wherebnb.entity.Rooms;
import com.example.wherebnb.entity.Users;
import com.example.wherebnb.exception.ApiException;
import com.example.wherebnb.exception.ExceptionEnum;
import com.example.wherebnb.repository.LikesRepository;
import com.example.wherebnb.repository.RoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomsService {

    private final RoomsRepository roomsRepository;
    private final LikesRepository likesRepository;

    // 숙소 등록
    public ResponseDto roomInsert(RoomsRequestDto roomsRequestDto, Users user) {
        roomsRepository.save(new Rooms(roomsRequestDto, user));
        return ResponseDto.setSuccess("숙소 등록을 완료하였습니다.", null);
    }

    // 숙소 수정
    public ResponseDto roomUpdate(Long id, RoomsRequestDto roomsRequestDto, Users user) {
        Rooms room = roomsRepository.findById(id).orElseThrow(  // 수정할 게시글 있는지 확인
                () -> new ApiException(ExceptionEnum.NOT_FOUND_ROOM));

        if (!room.getUser().equals(user))
            return ResponseDto.setBadRequest("숙소 수정을 할 수 없습니다.", null);

        room.roomUpdate(roomsRequestDto);
        return ResponseDto.setSuccess("숙소 수정을 완료하였습니다.", null);
    }

    // 숙소 삭제
    public ResponseDto roomDelete(Long id, Users user) {
        Rooms room = roomsRepository.findById(id).orElseThrow( // 삭제할 게시글 있는지 확인
                () -> new ApiException(ExceptionEnum.NOT_FOUND_ROOM));

        if (!room.getUser().equals(user))
            return ResponseDto.setBadRequest("숙소 수정을 할 수 없습니다.", null);

        roomsRepository.delete(room);
        return ResponseDto.setSuccess("숙소 삭제를 완료하였습니다.", null);
    }

    public ResponseDto roomLikes(Long id, Users user) {
        Rooms room = roomsRepository.findById(id).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ROOM));
        boolean likeStatus = true;

        if (likesRepository.existsByUserIdAndRoomsId(user.getId(), room.getId())) { // 이미 좋아요한 경우 취소
            Likes likes = likesRepository.findByUserIdAndRoomsId(user.getId(), room.getId());
            likesRepository.delete(likes);
            likeStatus = false;
        } else
            likesRepository.save(new Likes(room, user));

        room.updateLikes(likeStatus);
        return ResponseDto.setSuccess("좋아요를 눌렀습니다.", likeStatus);
    }
}
