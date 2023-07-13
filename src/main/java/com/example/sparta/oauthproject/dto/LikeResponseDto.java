package com.example.sparta.oauthproject.dto;

import com.example.sparta.oauthproject.entity.Like;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeResponseDto {

    private Long id;
    private Long postId;
    private Long userId;

    public LikeResponseDto(Like like) {
        this.id = like.getId();
        this.postId = like.getPost().getId();
        this.userId = like.getUser().getId();

    }
}