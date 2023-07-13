package com.example.sparta.oauthproject.controller;

import com.example.sparta.oauthproject.exception.ApiResponseDto;
import com.example.sparta.oauthproject.dto.LikeResponseDto;
import com.example.sparta.oauthproject.security.UserDetailsImpl;
import com.example.sparta.oauthproject.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/post/{postId}/like")
    public LikeResponseDto createPostLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return likeService.createPostLike(postId, userDetails);
    }

    @DeleteMapping("/post/like/{likeId}")
    public ResponseEntity<ApiResponseDto> deletePostLike(@PathVariable Long likeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.deletePostLike(likeId, userDetails.getUser()));
    }
}