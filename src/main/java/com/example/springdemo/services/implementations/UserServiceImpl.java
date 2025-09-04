package com.example.springdemo.services.implementations;

import com.example.springdemo.base.response.ResponseMessage;
import com.example.springdemo.base.response.ResponseObj;
import com.example.springdemo.dto.LoginRequestDto;
import com.example.springdemo.dto.LoginResponseDto;
import com.example.springdemo.dto.UserDto;
import com.example.springdemo.exceptions.ApiErrorException;
import com.example.springdemo.models.Users;
import com.example.springdemo.repositories.UserRepository;
import com.example.springdemo.security.UserPrinciple;
import com.example.springdemo.services.UserService;
import com.example.springdemo.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseObj<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        final var user = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCaseAndStatusTrue(loginRequestDto.username(), loginRequestDto.username()).orElseThrow(() ->
                new ApiErrorException(401, "Invalid username or password"));
        if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            throw new ApiErrorException(401, "Invalid username or password");
        }
        final var userAuthorities = user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).toList();
        final var userDetail = new UserPrinciple(
                user.getId(), user.getUsername(), user.getPassword(), userAuthorities
        );
        final var token = jwtTokenUtil.generateToken(userDetail);
        return new ResponseObj<>(new LoginResponseDto(token));
    }

    @Override
    public ResponseMessage registerUser(Users request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(request);
        return new ResponseMessage();
    }

    @Override
    public ResponseMessage updateUser(Long id, UserDto request) {
        final var user = userRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new ApiErrorException(404, "User not found"));
        var userUpdated = request.updateUser(user);
        userRepository.save(userUpdated);
        return new ResponseMessage();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserPrinciple();
    }
}
