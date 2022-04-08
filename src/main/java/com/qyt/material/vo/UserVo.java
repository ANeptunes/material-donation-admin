package com.qyt.material.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@Data
public class UserVo {
    // 用户id
    @Id
    private Long id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 昵称
    private String nickname;
    // 头像
    private String avatar;
    // 真实姓名
    private String realName;
    // 性别
    private Integer sex;
    // 邮箱
    private String email;
    // 电话
    private String phone;
    // 省份证
    private String identity;
    // 生日
    private String birthday;
    // 地址
    private String address;

    // 用户唯一ID
    private String uid;
}
