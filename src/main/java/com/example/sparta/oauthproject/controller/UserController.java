package com.example.sparta.oauthproject.controller;

import com.example.sparta.oauthproject.dto.ApiResponseDto;
import com.example.sparta.oauthproject.dto.UserRequestDto;
import com.example.sparta.oauthproject.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.example.sparta.oauthproject.service.UserService;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;

    }
    @PostMapping("/auth/signup")
    public ApiResponseDto signupUser(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ApiResponseDto apiResponseDto = new ApiResponseDto("계정생성", 200);
        if(fieldErrors.size() > 0){
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                apiResponseDto = new ApiResponseDto("계정규칙이 올바르지 않습니다.", 400);
                log.error(apiResponseDto.getMsg(),apiResponseDto.getStatusCode());
            }
            return apiResponseDto;
        }
        String respone = userService.signupUser(userRequestDto);
        return apiResponseDto;
    }

}
