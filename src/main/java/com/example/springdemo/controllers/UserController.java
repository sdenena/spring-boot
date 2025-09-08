package com.example.springdemo.controllers;

import com.example.springdemo.base.response.ResponseMessage;
import com.example.springdemo.dto.UserCreateDto;
import com.example.springdemo.dto.UserUpdateDto;
import com.example.springdemo.services.UserService;
import com.example.springdemo.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.MAIN_PATH + "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseMessage registerUser(@RequestBody UserCreateDto userDto) {
        return userService.registerUser(userDto.toUser());
    }

    @PutMapping("/{id}")
    public ResponseMessage updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userDto) {
        return userService.updateUser(id, userDto);
    }
}
