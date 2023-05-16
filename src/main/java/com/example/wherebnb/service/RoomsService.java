package com.example.wherebnb.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.dto.host.HostResponseDto;
import com.example.wherebnb.dto.room.RoomsRequestDto;
import com.example.wherebnb.entity.*;
import com.example.wherebnb.exception.ApiException;
import com.example.wherebnb.exception.ExceptionEnum;
import com.example.wherebnb.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoomsService {

    private final RoomsRepository roomsRepository;
    private final RoomsInfoRepository roomsInfoRepository;
    private final LikesRepository likesRepository;
    private final NotificationService notificationService;
    private final FilesRepository filesRepository;
    private final UserRepository userRepository;
    private final RoomsKeywordMappingRepository roomsKeywordMappingRepository;

    private static final String S3_BUCKET_PREFIX = "S3";

    @Value(("${cloud.aws.s3.bucket}"))
    private String bucketName;
    private final AmazonS3 amazonS3;

    public ResponseDto<?> roomInsert(RoomsRequestDto roomsRequestDto, Users user, List<MultipartFile> images) throws IOException {
        Rooms rooms = Rooms.builder().roomsRequestDto(roomsRequestDto).user(user).build();
        RoomsInfo roomsInfo = RoomsInfo.builder().roomsRequestDto(roomsRequestDto).rooms(rooms).build();

        rooms.setImageFile(fileFactory(images, rooms));
        roomsInfo.setPeriod(rooms.getCheckInDate(), rooms.getCheckOutDate());

        roomsKeywordMappingRepository.save(new RoomsKeywordMapping(new Keyword(roomsRequestDto.getKeyword1()), rooms));
        if(roomsRequestDto.getKeyword2().equals("null"))
            roomsKeywordMappingRepository.save(new RoomsKeywordMapping(new Keyword(roomsRequestDto.getKeyword2()), rooms));

        roomsRepository.save(rooms);
        roomsInfoRepository.save(roomsInfo);
        return ResponseDto.setSuccess("숙소 등록을 완료하였습니다.", null);
    }

    public ResponseDto<?> roomUpdate(Long id, RoomsRequestDto roomsRequestDto, Users user, List<MultipartFile> images) throws IOException {
        Rooms room = roomsRepository.findById(id).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ROOM));

        if (!room.getUser().getId().equals(user.getId()))
            return ResponseDto.setBadRequest("숙소 수정을 할 수 없습니다.", null);

        RoomsInfo roomsInfo = roomsInfoRepository.findByRoomId(room.getId()).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ROOM));
//
//        RoomsKeywordMapping roomsKeywordMapping = new RoomsKeywordMapping(new Keyword(roomsRequestDto.getKeyword1()), rooms));
//        if(roomsRequestDto.getKeyword2().equals("null"))
//            roomsKeywordMappingRepository.save(new RoomsKeywordMapping(new Keyword(roomsRequestDto.getKeyword2()), rooms));
//
//        List<RoomsKeywordMapping> roomsKeywordMappingList = roomsKeywordMappingRepository.findByRoomsId(room.getId()).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ROOM));
//        roomsKeywordMappingList =

        filesRepository.deleteByRoomId(id);
        room.setImageFile(fileFactory(images, room));
        room.roomsUpdate(roomsRequestDto);
        roomsInfo.roomsInfoUpdate(roomsRequestDto);
        return ResponseDto.setSuccess("숙소 수정을 완료하였습니다.", null);
    }

    public ResponseDto<?> roomDelete(Long id, Users user) {
        Rooms room = roomsRepository.findById(id).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ROOM));

        if (!room.getUser().getId().equals(user.getId()))
            return ResponseDto.setBadRequest("숙소 삭제를 할 수 없습니다.", null);

        filesRepository.deleteByRoomId(id);
        roomsRepository.delete(room);
        return ResponseDto.setSuccess("숙소 삭제를 완료하였습니다.", null);
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<HostResponseDto>> getAllLikeRooms(Users user) {
        List<HostResponseDto> likeRoomsList = likesRepository.findAllByUserId(user.getId()).stream()
                .map(x->new HostResponseDto(x.getRoomsInfo().getRooms())).collect(Collectors.toList());
        return ResponseDto.setSuccess("좋아요한 숙소 조회를 완료하였습니다.", likeRoomsList);
    }

    public ResponseDto<Boolean> roomLikes(Long id, Users from) {
        RoomsInfo roomsInfo = roomsInfoRepository.findByRoomId(id).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_ROOM));
        boolean likeStatus = true;

        if (likesRepository.existsByUserIdAndRoomsId(from.getId(), roomsInfo.getRooms().getId())) {
            Likes likes = likesRepository.findByUserIdAndRoomsId(from.getId(), roomsInfo.getRooms().getId());
            likesRepository.delete(likes);
            likeStatus = false;
        } else {
            Likes likes = new Likes(roomsInfo, from);
            likesRepository.save(likes);
            notificationService.notifyLikeEvent(likes, roomsInfo.getRooms().getUser()); // 좋아요 알림 보내기
        }

        roomsInfo.updateLikes(likeStatus);
        return ResponseDto.setSuccess("좋아요를 눌렀습니다.", likeStatus);
    }

    public List<ImageFile> fileFactory(List<MultipartFile> images, Rooms rooms) throws IOException {
        List<ImageFile> imageFileList = new ArrayList<>();

        for (MultipartFile image : images) {
            LocalDateTime now = LocalDateTime.now();
            int hour = now.getHour();
            int minute = now.getMinute();
            int second = now.getSecond();
            int millis = now.get(ChronoField.MILLI_OF_SECOND);

            String imageUrl = null;
            String newFileName = "image" + hour + minute + second + millis;
            // substring으로 개선하기
            String fileExtension = '.' + image.getOriginalFilename().replaceAll("^.*\\.(.*)$", "$1");
            String imageName = S3_BUCKET_PREFIX + newFileName + fileExtension;

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(image.getContentType());
            objectMetadata.setContentLength(image.getSize());

            InputStream inputStream = image.getInputStream();

            amazonS3.putObject(new PutObjectRequest(bucketName, imageName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            imageUrl = amazonS3.getUrl(bucketName, imageName).toString();

            imageFileList.add(new ImageFile(imageUrl, image.getOriginalFilename(), imageName, rooms));
        }
        return imageFileList;
    }

    public ResponseDto<?> forTest(Users from) {
        Users to = userRepository.findByUsername(from.getUsername()).orElseThrow(()->new ApiException(ExceptionEnum.NOT_FOUND_ROOM));
        notificationService.notifyMe(from, to);
        return ResponseDto.setSuccess("SSE 테스트용 코드!", null);
    }
}
