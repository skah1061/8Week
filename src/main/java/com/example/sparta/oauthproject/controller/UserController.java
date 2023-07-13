package com.example.sparta.oauthproject.controller;

import com.example.sparta.oauthproject.exception.AccountOfTheWrongRules;
import com.example.sparta.oauthproject.exception.ApiResponseDto;
import com.example.sparta.oauthproject.dto.UserRequestDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.example.sparta.oauthproject.service.UserService;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final MessageSource messageSource;

    public UserController(UserService userService,MessageSource messageSource){
        this.userService = userService;
        this.messageSource = messageSource;
    }
    @PostMapping("/auth/signup")
    public String signupUser(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ApiResponseDto apiResponseDto = new ApiResponseDto("계정생성", 200);
        if(fieldErrors.size() > 0){
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                throw new AccountOfTheWrongRules(messageSource.getMessage(
                        "acount.state.error",
                        null,
                        "Wrong Acount Rule",
                        Locale.getDefault()
                    )
                );
//                apiResponseDto = new ApiResponseDto("계정규칙이 올바르지 않습니다.", 400);
            }

        }
        String respone = userService.signupUser(userRequestDto);
        return respone;
    }

}
