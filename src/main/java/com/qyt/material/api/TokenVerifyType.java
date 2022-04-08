package com.qyt.material.api;

import org.springframework.stereotype.Component;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */
@Component
public class TokenVerifyType {
    /**
     * 0 验证失败（token不合法） 1 已过期 2 验证成功
     */
    public static final int FAIL = 0;
    public static final int EXPIRED = 1;
    public static final int SUCCEESS = 2;
}
