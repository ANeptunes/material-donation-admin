package com.qyt.material.dto;

import lombok.Data;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/29 17:02
 * @Version 1.0
 */
@Data
public class VerifyByEmailFormDto {
    private String email;
    private String verifyCode;
    private String uid;
}
