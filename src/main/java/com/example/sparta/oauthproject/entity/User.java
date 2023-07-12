package com.example.sparta.oauthproject.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name="users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @Id
    Long id;


    @Column(name="username", nullable = false, unique = true)
    String username;
    @Column(name="password", nullable = false)
    String password;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    UserRoleEnum role;


    public User(){


    }
    public User(String username,String password, UserRoleEnum role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
