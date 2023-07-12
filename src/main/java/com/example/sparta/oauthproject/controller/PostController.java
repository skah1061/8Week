package com.example.sparta.oauthproject.controller;

import com.example.sparta.oauthproject.dto.PostRequestDto;
import com.example.sparta.oauthproject.dto.PostResponseDto;
import com.example.sparta.oauthproject.security.UserDetailsImpl;
import com.example.sparta.oauthproject.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/post")
    public PostResponseDto createBlog(HttpServletRequest httpServletRequest, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createBlog(httpServletRequest,requestDto, userDetails);
    }
    @GetMapping("/post/{id}")
    public PostResponseDto detailInquiry(@PathVariable Long id){
        return postService.detailPost(id);
    }
    @GetMapping("/post")
//    @ResponseBody
    public List<PostResponseDto> getBlog() {
        return postService.getPost();
    }

    @PutMapping("/post/{id}")
    public PostRequestDto updateBlog(HttpServletRequest req,@PathVariable Long id,@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(req,id,postRequestDto, userDetails);
    }

    @DeleteMapping("/post/{id}")
    public String deletePost(HttpServletRequest req,@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(req,id,userDetails);
    }

}