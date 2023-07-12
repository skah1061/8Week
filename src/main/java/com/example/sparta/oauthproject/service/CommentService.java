package com.example.sparta.oauthproject.service;

import com.example.sparta.oauthproject.dto.CommentRequestDto;
import com.example.sparta.oauthproject.entity.Comment;
import com.example.sparta.oauthproject.entity.Post;
import com.example.sparta.oauthproject.entity.User;
import com.example.sparta.oauthproject.repository.CommentRepository;
import com.example.sparta.oauthproject.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository, PostService postService){
        this.commentRepository = commentRepository;
        this.postService = postService;
    }


    public void createComment(CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Post post = postService.findPost(commentRequestDto.getPostId());
        Comment comment = new Comment(commentRequestDto.getBody(), post, userDetails.getUser());

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Comment comment = findComment(id);
        if (userDetails.getUsername().equals(comment.getUser().getUsername())) {
            comment.setBody(commentRequestDto.getBody());
        } else {
            throw new IllegalArgumentException("직접쓴 글이 아니면 수정할 수 없습니다.");
        }
    }

    public void deleteComment(Long id, User user) {
        Comment comment = findComment(id);

        if (!(comment.getUser().getUsername().equals(user.getUsername()))) {
            throw new RejectedExecutionException();
        }

        commentRepository.delete(comment);
    }
    public Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글은 존재하지 않습니다."));

    }
}
