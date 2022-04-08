package com.qyt.material.dto;

import lombok.Data;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/29 18:45
 * @Version 1.0
 */
@Data
public class UpdatePasswordDto {
    private String password;
    private String checkPassword;
    private String uid;
}
