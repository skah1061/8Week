package com.example.sparta.oauthproject.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    //    @Size(min=4,max=10)
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    String username;
    //    @Size(min=8,max=15)
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    String password;
    //Pattern이용하여 구현하기
    private Boolean admin = false;

    private String adminToken = "";

    public Boolean getAdmin(){
        return admin;
    }
}
