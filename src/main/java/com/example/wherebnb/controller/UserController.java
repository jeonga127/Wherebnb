package com.example.wherebnb.controller;

import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseDto kakaoLogin(@RequestParam("code") String code, HttpServletResponse response) throws JsonProcessingException {
        return userService.kakaoLogin(code, response);
    }
}
