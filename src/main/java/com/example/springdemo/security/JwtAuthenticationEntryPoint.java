package com.example.springdemo.security;

import com.example.springdemo.exceptions.ErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorException exception = new ErrorException();
        log.error("==> commence error: {}", exception.getMessage());
        exception.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        exception.setMessage(
                request.getAttribute("invalid") != null
                        ? request.getAttribute("invalid").toString()
                        : authException.getLocalizedMessage()
        );

        if (response.getStatus() == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            exception.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            exception.setMessage("System Error");
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);

        OutputStream out = response.getOutputStream();
        new ObjectMapper().writeValue(out, exception);
        out.flush();
    }
}
