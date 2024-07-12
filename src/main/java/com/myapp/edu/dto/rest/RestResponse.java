package com.myapp.edu.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestResponse<T> {

    private String msg;
    private int code;
    private T data;

    public RestResponse(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }
}
