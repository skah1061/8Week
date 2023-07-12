package com.example.sparta.oauthproject.repository;

import com.example.sparta.oauthproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository  extends JpaRepository<Post, Long> {
    //        List<Post> findAllByOrderBywriteDateDesc();
    List<Post> findAllByOrderByCreateAtDesc();
}