package com.qyt.material.service;

import com.qyt.material.dto.UserLoginDto;
import com.qyt.material.dto.UserRegisterDto;
import com.qyt.material.pojo.User;
import com.qyt.material.vo.UserBaseInfoVo;
import com.qyt.material.vo.UserVo;

public interface UserService {

    /**
     * 根据用户名获取用户信息
     *
     * @param name 用户名
     * @return 用户信息
     */
    User getUserByName(String name);

    /**
     * 获取一个用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getUserInfo(String username);

    /**
     * 获取一个用户基本信息
     *
     * @param uid 用户id
     * @return 用户信息
     */
    User getUserBaseInfo(String uid);

    /**
     * 注册用户
     *
     * @param userRegisterDto 用户注册信息
     * @return 更新记录数
     */
    int register(UserRegisterDto userRegisterDto);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    String login(String username, String password);

    /**
     * 修改用户信息
     *
     * @param userBaseInfoVo 用户信息
     * @return 记录条数
     */
    int update(UserBaseInfoVo userBaseInfoVo);

    /**
     * 修改密码
     *
     * @param password 密码
     * @param uid      用户id
     * @return 记录条数
     */
    int updatePassword(String password, String uid);

    /**
     * 修改绑定邮箱
     *
     * @param email 邮箱
     * @param uid   用户id
     * @return 更新记录数
     */
    int updateBindEmail(String email, String uid);

    int cancelAccount(String uid);
}
