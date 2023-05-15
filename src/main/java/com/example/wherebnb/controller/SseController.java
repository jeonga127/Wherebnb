package com.example.wherebnb.controller;

import com.example.wherebnb.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SseController {

    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
    private final JwtUtil jwtUtil;

    @CrossOrigin
    @GetMapping(value = "/sub", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe (@RequestParam("token") String token){

        Long kakaoId = Long.valueOf(jwtUtil.getUserInfoFromToken(token));
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        try{
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        sseEmitters.put(kakaoId,sseEmitter);

        sseEmitter.onCompletion(()->sseEmitters.remove(kakaoId));
        sseEmitter.onTimeout(()->sseEmitters.remove(kakaoId));
        sseEmitter.onError((e)-> sseEmitters.remove(kakaoId));

        return sseEmitter;
    }
}
