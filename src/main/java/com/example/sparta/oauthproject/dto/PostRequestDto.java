package com.example.sparta.oauthproject.dto;

import com.example.sparta.oauthproject.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {
    String title;
    String content;
    User user;
}
