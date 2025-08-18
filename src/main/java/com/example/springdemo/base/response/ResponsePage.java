package com.example.springdemo.base.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePage<T> {
    private List<T> results;
    private long total;
    private ResponseMessage response = new ResponseMessage().success();

    public ResponsePage(List<T> results, long total) {
        this.results = results;
        this.total = total;
    }
}
