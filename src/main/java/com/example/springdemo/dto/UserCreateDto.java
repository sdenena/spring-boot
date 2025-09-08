package com.example.springdemo.dto;

import com.example.springdemo.models.Role;
import com.example.springdemo.models.Users;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    private Long id;
    private String firstName;
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    private String password;
    private List<Long> roles;

    public Users toUser() {
        var roleObjs = roles.stream().map(Role::new).collect(Collectors.toSet());
        return new Users(id, firstName, lastName, email, username, password, roleObjs);
    }
}
