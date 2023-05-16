package com.example.wherebnb.service;

import com.example.wherebnb.dto.host.HostDetailResponseDto;
import com.example.wherebnb.dto.host.HostRequestDto;
import com.example.wherebnb.dto.host.HostResponseDto;
import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.entity.Rooms;
import com.example.wherebnb.entity.RoomsInfo;
import com.example.wherebnb.entity.Users;
import com.example.wherebnb.exception.ApiException;
import com.example.wherebnb.exception.ExceptionEnum;
import com.example.wherebnb.repository.LikesRepository;
import com.example.wherebnb.repository.RoomsInfoRepository;
import com.example.wherebnb.repository.RoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HostService {

    private final RoomsRepository roomsRepository;
    private final RoomsInfoRepository roomsInfoRepository;
    private final LikesRepository likesRepository;

    public ResponseDto<List<HostResponseDto>> getAllRooms(Pageable pageable) {
        List<HostResponseDto> roomList = roomsRepository.findAll(pageable).getContent()
                .stream().map(HostResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("전체 조회 성공", roomList);
    }

    public ResponseDto<List<HostResponseDto>> getUsersAllRooms(Pageable pageable, Users user) {
        List<HostResponseDto> roomList = roomsRepository.findAll(pageable).getContent().stream()
                .map(x-> new HostResponseDto(x,likesRepository.existsByUserIdAndRoomsId(user.getId(), x.getId())))
                .collect(Collectors.toList());
        return ResponseDto.setSuccess("전체 조회 성공", roomList);
    }

    public ResponseDto<HostDetailResponseDto> getRoomDetail(Long id) {
        RoomsInfo roomsInfo = roomsInfoRepository.findByRoomId(id).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ROOM));
        HostDetailResponseDto hostDetailResponseDto = new HostDetailResponseDto().toHostResponseDto(roomsInfo);
        return ResponseDto.setSuccess("상세 조회 성공", hostDetailResponseDto);
    }

//    public ResponseDto<List<HostResponseDto>> chooseSearch(String keyword1, String keyword2, Pageable pageable){
//        List<HostResponseDto> roomList =  roomsRepository.findAllByKeyword1OrKeyword2(keyword1, keyword2, pageable).stream().map(HostResponseDto::new).collect(Collectors.toList());
//        return ResponseDto.setSuccess("키워드 검색 성공", roomList);
//    }
//
//    public ResponseDto<List<HostResponseDto>> chooseUsersSearch(String keyword1, String keyword2, Users user, Pageable pageable) {
//        List<HostResponseDto> roomList = roomsRepository.findAllByKeyword1OrKeyword2(keyword1, keyword2, pageable).stream()
//                .map(x->new HostResponseDto(x, likesRepository.existsByUserIdAndRoomsId(user.getId(), x.getId())))
//                .collect(Collectors.toList());
//        return ResponseDto.setSuccess("키워드 검색 성공", roomList);
//    }

    public ResponseDto<List<HostResponseDto>> getRoomsByCondition(HostRequestDto hostreqeuestdto, Pageable pageable) {
        List<HostResponseDto> roomsListByCondition = ConditionCheck(hostreqeuestdto, pageable).stream().map(HostResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("조건 검색 성공",roomsListByCondition);
    }

    public ResponseDto<List<HostResponseDto>> getRoomsByUserAndCondition(HostRequestDto hostreqeuestdto, Users user, Pageable pageable) {
        List<HostResponseDto> roomsListByCondition =  ConditionCheck(hostreqeuestdto, pageable).stream()
                .map(x-> new HostResponseDto(x, likesRepository.existsByUserIdAndRoomsId(user.getId(), x.getId()))).collect(Collectors.toList());
        return ResponseDto.setSuccess("조건 검색 성공",roomsListByCondition);
    }

    public List<Rooms> ConditionCheck(HostRequestDto hostreqeuestdto, Pageable pageable){
        LocalDate checkInDate = LocalDate.parse(hostreqeuestdto.getCheckInDate(), DateTimeFormatter.ISO_DATE);
        LocalDate checkOutDate = LocalDate.parse(hostreqeuestdto.getCheckOutDate(), DateTimeFormatter.ISO_DATE);
        int guestNum = hostreqeuestdto.getAdultsNum() + hostreqeuestdto.getChildrenNum();

        List<Rooms> roomsList;
        int period = 0;
        if (!"not_flexible".equals(hostreqeuestdto.getFlexibleTripLengths())) {
            if ("one_month".equals(hostreqeuestdto.getFlexibleTripLengths())) {
                period = 30;
            } else if ("one_week".equals(hostreqeuestdto.getFlexibleTripLengths())) {
                period = 7;
            } else {
                throw new ApiException(ExceptionEnum.NOT_FOUND_CONDITION);
            }
            roomsList = roomsInfoRepository.findAllByPeriodGreaterThanEqual(period, pageable).stream().map(x->x.getRooms()).collect(Collectors.toList());
        } else {
            roomsList = roomsRepository.findByCheckInDateGreaterThanEqualAndCheckOutDateGreaterThanEqual(checkInDate, checkOutDate, pageable);
        }

        roomsList.retainAll(roomsInfoRepository.findByGuestNumGreaterThanEqualAndInfantExistAndPetExist(guestNum, hostreqeuestdto.isInfantExist(), hostreqeuestdto.isPetExist(), pageable)
                .stream().map(x->x.getRooms()).collect(Collectors.toList()));
        return roomsList;
    }
}