package com.example.springdemo.services;

import com.example.springdemo.base.response.ResponseMessage;
import com.example.springdemo.base.response.ResponseObj;
import com.example.springdemo.dto.LoginRequestDto;
import com.example.springdemo.dto.LoginResponseDto;
import com.example.springdemo.dto.UserDto;
import com.example.springdemo.models.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    ResponseObj<LoginResponseDto> login(LoginRequestDto loginRequestDto);

    ResponseMessage registerUser(Users request);

    ResponseMessage updateUser(Long id, UserDto request);
}
