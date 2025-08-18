package com.example.springdemo.base.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObj<T> {
    private T results;
    private ResponseMessage response = new ResponseMessage().success();

    public ResponseObj(T results) {
        this.results = results;
    }
}
