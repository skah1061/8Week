package com.example.sparta.oauthproject.controller;

import com.example.sparta.oauthproject.exception.ApiResponseDto;
import com.example.sparta.oauthproject.dto.CommentRequestDto;
import com.example.sparta.oauthproject.security.UserDetailsImpl;
import com.example.sparta.oauthproject.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }
    @ResponseBody
    @PostMapping("/comment")         //댓글 작성
    public ApiResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.createComment(commentRequestDto, userDetails);
        return new ApiResponseDto("댓글 작성 완료", HttpStatus.OK.value());
    }
    @ResponseBody
    @PutMapping("/comment/{id}")            //댓글 수정 수정
    public void updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.updateComment(id, commentRequestDto, userDetails);
    }

    @ResponseBody
    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(id, userDetails.getUser());
    }


}
