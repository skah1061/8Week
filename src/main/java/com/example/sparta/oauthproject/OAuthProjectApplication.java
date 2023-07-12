package com.example.sparta.oauthproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OAuthProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthProjectApplication.class, args);
    }

}
