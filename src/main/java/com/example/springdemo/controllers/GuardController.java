package com.example.springdemo.controllers;

import com.example.springdemo.base.response.ResponseObj;
import com.example.springdemo.dto.LoginRequestDto;
import com.example.springdemo.dto.LoginResponseDto;
import com.example.springdemo.services.UserService;
import com.example.springdemo.utils.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(Constant.MAIN_PATH + "/guards")
@RequiredArgsConstructor
public class GuardController {
    private final UserService userService;

    @PostMapping("/log-in")
    public ResponseObj<LoginResponseDto> logIn(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }
}
