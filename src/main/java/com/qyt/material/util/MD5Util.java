package com.qyt.material.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

@Component
public class MD5Util {
    /**
     * 密码加密
     */
    public String md5Encryption(String password) {
        String algorithmName = "MD5";//加密算法
        int hashIterations = 1024;//加密次数
        final String saltKey = "QIUYONGTU19990826";
        Object salt = ByteSource.Util.bytes(saltKey);
        SimpleHash simpleHash = new SimpleHash(algorithmName, password, salt, hashIterations);
        return simpleHash + "";
    }


//    public static String md5ByBCryptPasswordEncoder(String password) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        return passwordEncoder.encode(password);
//    }
}
