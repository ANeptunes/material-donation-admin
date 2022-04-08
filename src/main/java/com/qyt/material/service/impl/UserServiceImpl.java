package com.qyt.material.service.impl;

import com.qyt.material.dto.UserRegisterDto;
import com.qyt.material.mapper.UserMapper;
import com.qyt.material.pojo.User;
import com.qyt.material.service.UserService;
import com.qyt.material.util.MD5Util;
import com.qyt.material.util.TokenUtil;
import com.qyt.material.vo.UserBaseInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private TokenUtil tokenUtil;

    @Resource
    private MD5Util md5Util;

    @Override
    public User getUserByName(String name) {
        return userMapper.selectByName(name);
    }

    @Override
    public User getUserInfo(String username) {
        return userMapper.selectByName(username);
    }

    @Override
    public User getUserBaseInfo(String uid) {
        return userMapper.selectByUid(uid);
    }

    /**
     * 注册用户
     *
     * @param userRegisterDto 需要注册的用户
     * @return 插入数据的行数
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public int register(UserRegisterDto userRegisterDto) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterDto, user);
        String encryptionPass = md5Util.md5Encryption(userRegisterDto.getPassword());
        String uid = String.valueOf(System.currentTimeMillis()) + (int) ((Math.random() * 9 + 1) * 100000);
        user.setPassword(encryptionPass);
        user.setCreateTime(new Date());
        user.setStatus(1);
        user.setType(0);
        user.setUid(uid);
        return userMapper.insert(user);
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public String login(String username, String password) {
        String encryptionPass = md5Util.md5Encryption(password);
        System.out.println(password);
        System.out.println(encryptionPass);
        User user = userMapper.selectByNameAndPwd(username, encryptionPass);
        if (user != null) {
            // 生成toke
            String token = tokenUtil.token(username);
            System.out.println(token);
            return token;
        } else {
            return null;
        }

    }

    /**
     * 修改用户基本信息
     *
     * @param userBaseInfoVo 用户信息
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public int update(UserBaseInfoVo userBaseInfoVo) {
        User user = new User();
        BeanUtils.copyProperties(userBaseInfoVo, user);
        user.setModifiedTime(new Date());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int updatePassword(String password, String uid) {
        return userMapper.updatePassword(password, uid);
    }

    @Override
    public int updateBindEmail(String email, String uid) {
        return userMapper.updateBindEmail(email, uid);
    }

    @Override
    public int cancelAccount(String uid) {
        return userMapper.cancelAccount(uid);
    }
}
