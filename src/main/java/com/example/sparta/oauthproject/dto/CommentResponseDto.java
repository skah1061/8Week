package com.example.sparta.oauthproject.dto;

import com.example.sparta.oauthproject.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String profilename;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.body = comment.getBody();
        this.createdAt = comment.getCreateAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}