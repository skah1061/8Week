package com.example.sparta.oauthproject.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    Long postId;
    String body;

    public void setBody(String body) {
        this.body = body;
    }
}