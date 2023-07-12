package com.example.sparta.oauthproject.service;

import com.example.sparta.oauthproject.config.PasswordConfig;
import com.example.sparta.oauthproject.dto.UserRequestDto;
import com.example.sparta.oauthproject.entity.User;
import com.example.sparta.oauthproject.entity.UserRoleEnum;
import com.example.sparta.oauthproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String signupUser(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = passwordEncoder.encode(userRequestDto.getPassword());

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        UserRoleEnum role = UserRoleEnum.USER;

        if(userRequestDto.getAdmin()){
            if(!ADMIN_TOKEN.equals(userRequestDto.getAdminToken())){
                throw new IllegalArgumentException("관리자 암호가 틀립니다.");
            }
                role = UserRoleEnum.ADMIN;
        }


        User user = new User(username,password,role);
        userRepository.save(user);
        return "회원가입";
    }
}
