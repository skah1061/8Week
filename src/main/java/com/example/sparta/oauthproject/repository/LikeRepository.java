package com.example.sparta.oauthproject.repository;

import com.example.sparta.oauthproject.entity.Like;
import com.example.sparta.oauthproject.entity.Post;
import com.example.sparta.oauthproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
}
