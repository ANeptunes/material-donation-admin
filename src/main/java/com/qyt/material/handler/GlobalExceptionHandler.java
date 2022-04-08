package com.qyt.material.handler;

import com.qyt.material.api.Result;
import com.qyt.material.exception.AuthTokenErrorException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthTokenErrorException.class)
    public Result authorizationException(AuthTokenErrorException e) {
        return Result.failed(e.getCode(), e.getMessage());
    }
}
