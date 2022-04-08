package com.qyt.material.exception;

import lombok.Getter;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@Getter
public class AuthTokenErrorException extends RuntimeException {

    private Integer code;

    private Boolean success;

    public AuthTokenErrorException() {
    }

    public AuthTokenErrorException(String message) {
        super(message);
    }

    public AuthTokenErrorException(Integer code, Boolean success, String message) {
        super(message);
        this.code = code;
        this.success = success;
    }
}
