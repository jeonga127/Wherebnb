package com.example.wherebnb.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.dto.RoomsRequestDto;
import com.example.wherebnb.entity.Image;
import com.example.wherebnb.entity.Likes;
import com.example.wherebnb.entity.Rooms;
import com.example.wherebnb.entity.Users;
import com.example.wherebnb.exception.ApiException;
import com.example.wherebnb.exception.ExceptionEnum;
import com.example.wherebnb.repository.ImageRepository;
import com.example.wherebnb.repository.LikesRepository;
import com.example.wherebnb.repository.RoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomsService {

    private final RoomsRepository roomsRepository;
    private final LikesRepository likesRepository;
    private final NotificationService notificationService;
    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    private static final String S3_BUCKET_PREFIX = "S3";

    @Value("${aws.s3.bucket}")
    private String bucketName;


    // 숙소 등록
    public ResponseDto roomInsert(RoomsRequestDto roomsRequestDto, Users user, List<MultipartFile> images) throws Exception {

        List<Image> imageEntities = new ArrayList<>();

        if (images != null) {
            for (MultipartFile image : images) {
                String imageUrl = s3Service.uploadFile(image);
                Image newImage = new Image(imageUrl);
                imageEntities.add(newImage); // Add the new Image entity to the list
            }
        }else{
            return ResponseDto.setBadRequest("이미지 파일이 없습니다");
        }
        Rooms newRoom = new Rooms(roomsRequestDto, imageEntities, user);

        // Save the new Room entity
        Rooms savedRoom = roomsRepository.save(newRoom);

        // After saving the Room, save the Image entities with the reference to the saved Room
        for (Image imageEntity : imageEntities) {
            imageEntity.associateWithRoom(savedRoom); // Associate the Image with the saved Room
            imageRepository.save(imageEntity); // Save the Image entity
        }
        return ResponseDto.setSuccess("숙소 등록이 완료되었습니다.", null);
    }

    // 숙소 수정
    public ResponseDto roomUpdate(Long id, RoomsRequestDto roomsRequestDto, Users user, List<MultipartFile> images) {
        Rooms room = roomsRepository.findById(id).orElseThrow(  // Check if the room to be updated exists
                () -> new ApiException(ExceptionEnum.NOT_FOUND_ROOM));

        if (!room.getUser().equals(user))
            return ResponseDto.setBadRequest("숙소 수정을 할 수 없습니다.", null);

        // Delete the old images associated with the room
        for (Image oldImage : room.getImages()) {
            imageRepository.delete(oldImage);
        }

        // Upload new images and create Image entities
        List<Image> imageEntities = new ArrayList<>();
        if (images != null) {
            for (MultipartFile image : images) {
                String imageUrl = s3Service.uploadFile(image);
                Image newImage = new Image(imageUrl);
                imageEntities.add(newImage); // Add the new Image entity to the list
            }
        }

        room.roomUpdate(roomsRequestDto, imageEntities); // Update the room with the new details and images

        // Save the updated Room entity
        Rooms savedRoom = roomsRepository.save(room);

        // After saving the Room, save the new Image entities with the reference to the saved Room
        for (Image imageEntity : imageEntities) {
            imageEntity.associateWithRoom(savedRoom); // Associate the Image with the saved Room
            imageRepository.save(imageEntity); // Save the Image entity
        }

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
        } else { // 좋아요
            Likes likes = new Likes(room, user);
            likesRepository.save(likes);
            notificationService.notifyLikeEvent(likes); // 좋아요 알림 보내기
        }

        room.updateLikes(likeStatus);
        return ResponseDto.setSuccess("좋아요를 눌렀습니다.", likeStatus);
    }
}
