package com.example.wherebnb.service;

import com.example.wherebnb.entity.Likes;
import com.example.wherebnb.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.example.wherebnb.controller.SseController.sseEmitters;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    public void notifyLikeEvent(Likes likes, Users to){
        log.info("notifyMe 진입!");
        Long kakaoId = Long.valueOf(to.getKakaoId());

        for(Map.Entry<Long,SseEmitter> e : sseEmitters.entrySet())
            log.info("현재 존재하는 SSE Emitter : " + e.getKey() + " " + e.getValue());

        if(sseEmitters.containsKey(kakaoId)){
            log.info("notifyLike sseEmitters 찾음!");
            SseEmitter sseEmitter = sseEmitters.get(kakaoId);
            try{
                log.info("notifyLike sseEmitters 활동중!");

                sseEmitter.send(SseEmitter.event().name("notifyLike")
                                .data("data : { \"username\": " + likes.getUser().getUsername() + ", "
                                + "\"roomname\": " + likes.getRoomsInfo().getRoomName() + ", "
                                + "\"createdAt\": " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM월 dd일 a HH시 mm분"))));

                // writer.write("data: { \"message\" : \"number : "+ i + "\" }\n\n");
            } catch (Exception e) {
                sseEmitters.remove(kakaoId);
            }
        }
    }
    public void notifyMe(Users from, Users to){ // SSE 테스트용

        for(Map.Entry<Long,SseEmitter> e : sseEmitters.entrySet())
            log.info("현재 존재하는 SSE Emitter : " + e.getKey() + " " + e.getValue());

        if(sseEmitters.containsKey(Long.valueOf(to.getKakaoId()))){
            log.info("notifyMe sseEmitters 찾음!");
            SseEmitter sseEmitter = sseEmitters.get(Long.valueOf(to.getKakaoId()));
            try{
                log.info("notifyMe sseEmitters 활동중!");
                sseEmitter.send(SseEmitter.event().name("notifyMe")
                        .data(from.getUsername() +" 님으로부터의 알림!"));
            } catch (Exception e) {
                sseEmitters.remove(Long.valueOf(to.getKakaoId()));
            }
        }
    }
}
