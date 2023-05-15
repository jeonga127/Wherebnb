package com.example.wherebnb.service;

import com.example.wherebnb.entity.Likes;
import com.example.wherebnb.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static com.example.wherebnb.controller.SseController.sseEmitters;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final LikesRepository likesRepository;

    public void notifyLikeEvent(Likes likes){

        Long kakaoId = Long.valueOf(likes.getUser().getKakaoId());

        if(sseEmitters.containsKey(kakaoId)){
            SseEmitter sseEmitter = sseEmitters.get(kakaoId);
            try{
                sseEmitter.send(SseEmitter.event().name("notifyLike")
                        .data(likes.getUser().getUsername() +" 님이 내" + likes.getRooms().getRoomName() + "을 즐겨찾기 하셨습니다."));
            } catch (Exception e) {
                sseEmitters.remove(kakaoId);
            }
        }
    }
}
