package com.example.wherebnb.users.controller;

import com.example.wherebnb.global.dto.ResponseDto;
import com.example.wherebnb.users.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }
    @GetMapping("/login")
    public ResponseDto kakaoLogin(@RequestParam("code") String code, HttpServletResponse response) throws JsonProcessingException {
        return userService.kakaoLogin(code, response);
    }
}
