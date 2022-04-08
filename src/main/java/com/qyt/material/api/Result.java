package com.qyt.material.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    private Integer code;
    /**
     * 是否成功
     **/
    private Boolean success;
    private String msg;
    private T data;


    /**
     * 成功返回数据
     */
    public static <T> Result<T> success(Integer code) {
        return new Result<T>(code, true, null, null);
    }

    /**
     * 成功返回数据
     *
     * @param data 获取的数据
     */
    public static <T> Result<T> success(Integer code, T data) {
        return new Result<T>(code, true, null, data);
    }

    /**
     * 成功时不返回数据,返回提示信息
     *
     * @param message 提示信息
     */
    public static <T> Result<T> success(Integer code, String message) {
        return new Result<T>(code, true, message, null);
    }

    /**
     * 成功返回提示信息和数据
     *
     * @param message 提示信息
     * @param data    获取的数据
     */
    public static <T> Result<T> success(Integer code, String message, T data) {
        return new Result<T>(code, true, message, data);
    }


    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Result<T> failed(Integer code, String message) {
        return new Result<T>(code, false, message, null);
    }

}
