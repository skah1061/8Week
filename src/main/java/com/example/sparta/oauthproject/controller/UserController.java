package com.example.sparta.oauthproject.controller;

import com.example.sparta.oauthproject.dto.UserRequestDto;
import com.example.sparta.oauthproject.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
    public String signupUser(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0){
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                log.error(fieldError.getField() + " 필드 " + fieldError.getDefaultMessage());
            }
            return "redirect: /api/user/login-page";
        }
        String respone = userService.signupUser(userRequestDto);
        return respone;
    }

}
