package com.qyt.material.mapper;

import com.qyt.material.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 根据用户名查询用户
    User selectByName(String username);

    // 根据用户名和密码查询用户
    User selectByNameAndPwd(String username, String password);

    User selectByUid(String uid);

    int updatePassword(String password, String uid);

    int updateBindEmail(String email, String uid);

    int cancelAccount(String uid);
}
