package com.example.wherebnb.service;

import com.example.wherebnb.dto.ResponseDto;
import com.example.wherebnb.jwt.JwtUtil;
import com.example.wherebnb.dto.UserInfoDto;
import com.example.wherebnb.entity.Users;
import com.example.wherebnb.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public ResponseDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        //1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code);

        //2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        UserInfoDto userInfoDto = getUserInfo(accessToken);

        //3. user repository에 user가 있는지 확인 / 없다면 넣음
        if(!userRepository.existsByKakaoId(userInfoDto.getKakaoId()))
            userRepository.save(new Users(userInfoDto));

        //4. JWT 토큰 반환
        String token = jwtUtil.createToken(userInfoDto.getUsername());

        Cookie cookie = new Cookie("Authorization", token.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseDto.setSuccess("로그인 성공", userInfoDto.getUsername());
    }
    private String getToken(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "8047e89d739d21f2f220a5e8df94ddd0");
        body.add("redirect_uri", "http://localhost:8080/user/login");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST, kakaoTokenRequest, String.class);

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    // 토큰에서 사용자 정보 get
    private UserInfoDto getUserInfo(String token) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        // CORS 에러가 여기서 나는것같아요 그래서 결과가 없으니까 뒤의 SQL문도 줄줄이 실패하는 것 같습니당 (보통은 그 전에 중지되지 않나 싶긴 한데)
        HttpEntity<MultiValueMap<String, String>> UserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST, UserInfoRequest, String.class);

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String username = jsonNode.get("properties").get("nickname").asText();
        Long kakaoId = jsonNode.get("id").asLong();

        return new UserInfoDto(username, kakaoId);
    }
}
