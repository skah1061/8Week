package com.example.sparta.oauthproject.service;

import com.example.sparta.oauthproject.dto.ApiResponseDto;
import com.example.sparta.oauthproject.dto.LikeResponseDto;
import com.example.sparta.oauthproject.entity.Like;
import com.example.sparta.oauthproject.entity.Post;
import com.example.sparta.oauthproject.entity.User;
import com.example.sparta.oauthproject.repository.LikeRepository;
import com.example.sparta.oauthproject.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;


@Service
public class LikeService {

    private final LikeRepository likeRepository;

    private final PostService postService;

    public LikeService(LikeRepository likeRepository, PostService postService) {
        this.likeRepository = likeRepository;
        this.postService = postService;
    }

    public LikeResponseDto createPostLike(Long postId, UserDetailsImpl userDetails) {

        Post post = postService.findPost(postId);


        Like like = new Like(post, userDetails.getUser());

        // 1. 지금 user가 이 게시물에 좋아요를 누른적이 있는지 판단 함 -> throw 던지기
        // -> userId, postId 이거 둘다 같으면 Like를 누른적이

        if (likeRepository.findByUserAndPost(userDetails.getUser(), post).isEmpty()) {
            likeRepository.save(like);

        } else {
            Optional<Like> like_id = likeRepository.findByUserAndPost(userDetails.getUser(), post);


성            deletePostLike(like_id.get().getId(),userDetails.getUser());
            //throw new IllegalArgumentException("좋아요를 누른적이 있습니다.");
            return null;
        }

        return new LikeResponseDto(like);

    }


    public ApiResponseDto deletePostLike(Long likeId, User user) {


        Like like = findLike(likeId);

        if (!like.getUser().getUsername().equals(user.getUsername())) {
            throw new RejectedExecutionException("좋아요를 클릭한 유저가 아닙니다.");

        }

        likeRepository.delete(like);

        return new ApiResponseDto("삭제 성공!", 200);
    }

    public Like findLike(Long id) {
        return likeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("좋아요를 클릭한 적이 없습니다."));

    }
}