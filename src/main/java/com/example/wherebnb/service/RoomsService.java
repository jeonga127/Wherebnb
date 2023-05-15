package com.example.wherebnb.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.dto.RoomsRequestDto;
import com.example.wherebnb.entity.Rooms;
import com.example.wherebnb.entity.Users;
import com.example.wherebnb.global.ApiException;
import com.example.wherebnb.global.ExceptionEnum;
import com.example.wherebnb.repository.RoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomsService {

    private final RoomsRepository roomsRepository;

    private static final String S3_BUCKET_PREFIX = "S3";

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    // 숙소 등록
    public ResponseDto roomInsert(RoomsRequestDto roomsRequestDto, Users user, List<MultipartFile> image) throws IOException {

        Rooms rooms = new Rooms();
        for(MultipartFile images : image) {
            // 파일명 새로 부여를 위한 현재 시간 알아내기
            LocalDateTime now = LocalDateTime.now();
            int hour = now.getHour();
            int minute = now.getMinute();
            int second = now.getSecond();
            int millis = now.get(ChronoField.MILLI_OF_SECOND);

            String imageUrl = null;

            String newFileName = "image" + hour + minute + second + millis;
            String fileExtension = '.' + images.getOriginalFilename().replaceAll("^.*\\.(.*)$", "$1");
            String imageName = S3_BUCKET_PREFIX + newFileName + fileExtension;


            // 메타데이터 설정
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(images.getContentType());
            objectMetadata.setContentLength(images.getSize());

            InputStream inputStream = images.getInputStream();

            amazonS3.putObject(new PutObjectRequest(bucketName, imageName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            imageUrl = amazonS3.getUrl(bucketName, imageName).toString();
            rooms = rooms.builder()
                    .roomsRequestDto(roomsRequestDto)
                    .imageurl(imageUrl)
                    .user(user)
                    .build();
        roomsRepository.save(rooms);
        }
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
