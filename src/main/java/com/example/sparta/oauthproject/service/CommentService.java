package com.example.sparta.oauthproject.service;

import com.example.sparta.oauthproject.dto.CommentRequestDto;
import com.example.sparta.oauthproject.entity.Comment;
import com.example.sparta.oauthproject.entity.Post;
import com.example.sparta.oauthproject.entity.User;
import com.example.sparta.oauthproject.exception.NotFoundException;
import com.example.sparta.oauthproject.exception.NotHavePermission;
import com.example.sparta.oauthproject.repository.CommentRepository;
import com.example.sparta.oauthproject.security.UserDetailsImpl;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final MessageSource messageSource;

    public CommentService(CommentRepository commentRepository, PostService postService, MessageSource messageSource){
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.messageSource= messageSource;
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
            throw new NotHavePermission(messageSource.getMessage(
                    "not.have.permission",
                    null,
                    "Not have permission",
                    Locale.getDefault()
            ));
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
                new NotFoundException(messageSource.getMessage(
                        "not.found.exception",
                        null,
                        "NOT FOUND THIS COMMENT",
                        Locale.getDefault()
                )));

    }
}
