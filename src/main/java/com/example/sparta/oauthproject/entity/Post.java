package com.example.sparta.oauthproject.entity;

import com.example.sparta.oauthproject.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="post")
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;    //id
    @Column(name="title")
    String title;   //제목
    @Column(name = "username", nullable = false)
    String username;//작성자명
    @Column(name ="contents",nullable = false, length = 500)
    String contents;//작성내용
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE})
    private List<Like> LikeList = new ArrayList<>();


    public Post(PostRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContent();
        this.username = user.getUsername();
        this.user = user;
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUser().username;
        this.contents = requestDto.getContent();
    }
}

