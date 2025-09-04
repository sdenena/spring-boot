package com.example.springdemo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorException extends RuntimeException {
    private int code;
    private String message;


    @Override
    public String getLocalizedMessage() {
        return message;
    }
}
