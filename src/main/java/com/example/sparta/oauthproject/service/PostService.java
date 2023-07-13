package com.example.sparta.oauthproject.service;

import com.example.sparta.oauthproject.dto.PostRequestDto;
import com.example.sparta.oauthproject.dto.PostResponseDto;
import com.example.sparta.oauthproject.entity.Post;
import com.example.sparta.oauthproject.entity.UserRoleEnum;
import com.example.sparta.oauthproject.exception.NotFoundException;
import com.example.sparta.oauthproject.exception.NotHavePermission;
import com.example.sparta.oauthproject.exception.ProblemToken;
import com.example.sparta.oauthproject.jwt.JwtUtil;
import com.example.sparta.oauthproject.repository.PostRepository;
import com.example.sparta.oauthproject.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;
    private final MessageSource messageSource;
    @Autowired
    public PostService(PostRepository postRepository, JwtUtil jwtUtil, MessageSource messageSource){
        this.postRepository = postRepository;
        this.jwtUtil = jwtUtil;
        this.messageSource = messageSource;
    }
    public PostResponseDto createBlog(HttpServletRequest req, PostRequestDto requestDto, UserDetailsImpl userDetails) {
        String token = jwtUtil.getTokenFromRequest(req);

        if(StringUtils.hasText(token)) {
            token = jwtUtil.substringToken(token);
            if (jwtUtil.validateToken(token)) {
                Post post = new Post(requestDto, userDetails.getUser());

                //id체크
                Post savePost = postRepository.save(post);

                //Entity를 ResponseDto로
                PostResponseDto postResponseDto = new PostResponseDto(post);

                return postResponseDto;
            }
        }
        //엔티티로 변환해주는 단계
        return null;
    }

    public List<PostResponseDto> getPost() {
        //DB조회하기
        return postRepository.findAllByOrderByCreateAtDesc().stream().map(PostResponseDto::new).toList();
    }
    @Transactional
    public PostRequestDto updatePost(HttpServletRequest req, Long id, PostRequestDto requestDto, UserDetailsImpl userDetails){
        String username = userDetails.getUsername();

        Post post = findPost(id);
        if(isToken(req)) {
            if (post.getUser().getUsername().equals(username)||userDetails.getAuth().equals("ROLE_ADMIN")) {//혹은 관리자일 때 추가
                requestDto.setUser(userDetails.getUser());
                post.update(requestDto);

                return requestDto;
            } else {
                throw new NotHavePermission(messageSource.getMessage(
                        "not.have.permission",
                        null,
                        "You Do Not Have Permission",
                        Locale.getDefault()
                ));
            }
        }
        else{
            throw new ProblemToken(messageSource.getMessage(
                    "problem.token",
                    null,
                    "Problem With the CurrentToken",
                    Locale.getDefault()
            ));
        }
    }
    public String deletePost(HttpServletRequest req,Long id,UserDetailsImpl userDetails){

        Post post = findPost(id);
        if(isToken(req)){
            if(post.getUsername().equals(userDetails.getUsername())||userDetails.getAuth().equals("ROLE_ADMIN")) {
                postRepository.delete(post);

                return "삭제완료";
            }
            else {
                throw new NotHavePermission(messageSource.getMessage(
                        "not.have.permission",
                        null,
                        "You Do Not Have Permission",
                        Locale.getDefault()
                ));
            }
        }
        else{
            throw new ProblemToken(messageSource.getMessage(
                    "problem.token",
                    null,
                    "Problem With the CurrentToken",
                    Locale.getDefault()
            ));
        }
    }
    public PostResponseDto detailPost(Long id) {
        Post post = findPost(id);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }
    public Post findPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(
                        "not.found.exception",
                        null,
                        "NOT FOUND ID",
                        Locale.getDefault()
                ))

        );
        return post;
    }
    private boolean isToken(HttpServletRequest req){
        String token = jwtUtil.getTokenFromRequest(req);
        if(StringUtils.hasText(token)){
            token =jwtUtil.substringToken(token);
            if(jwtUtil.validateToken(token)){
                return true;
            }

        }
        return false;
    }

}
