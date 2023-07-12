package com.example.sparta.oauthproject.dto;

import com.example.sparta.oauthproject.entity.Comment;
import com.example.sparta.oauthproject.entity.Post;
import com.example.sparta.oauthproject.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDto {
    private Long id;
    String title;
    String content;
    User user;
    private List<CommentResponseDto> comment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }
    public PostResponseDto(PostRequestDto postRequestDto, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContents();
        this.user = post.getUser();
        this.createdAt = post.getCreateAt();
        this.modifiedAt = post.getModifiedAt();
        this.comment = post.getCommentList()
                .stream()
                .map(CommentResponseDto::new)
                .toList();
    }
}
