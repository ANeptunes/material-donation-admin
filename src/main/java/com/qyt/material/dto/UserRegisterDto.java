package com.qyt.material.dto;

import lombok.Data;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@Data
public class UserRegisterDto {
    private String username;
    private String password;
    private String nickname;
    private String phone;
}
