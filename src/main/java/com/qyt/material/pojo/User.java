package com.qyt.material.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@Data
public class User {
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

    // 地址label值
    private String addressLabel;

    // 注册时间(账号注册的时间)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    // 修改时间(账号信息修改的时间)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifiedTime;
    // 用户状态(0锁定 1有效)
    private Integer status;
    // 用户类别(0普通用户  1系统管理员)
    private Integer type;

    // 实名认证类型（0 未认证 1 个人认证 2 企业认证）
    private int verified;

    // 详细地址
    private String detailAddress;

    // 个人描述
    private String description;

    // 用户唯一ID
    private String uid;
}
