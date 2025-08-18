package com.example.springdemo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomException extends RuntimeException {
    private int code;
    private String message;

    @Override
    public String getLocalizedMessage() {
        return message;
    }
}
