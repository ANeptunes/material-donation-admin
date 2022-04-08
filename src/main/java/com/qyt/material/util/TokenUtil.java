package com.qyt.material.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    /**
     * 设置过期时间  [`单位毫秒`]
     * 设置token有效期为15分钟
     */
    private static final long EXPIRE_DATE = 15 * 60 * 1000;
    /**
     * token秘钥
     */
    private static final String TOKEN_SECRET = "QIUYONGTU19990826";

    public String token(String username) {

        String token;
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_DATE);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //携带username，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("username", username)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }

    /**
     * @param token    token
     * @param username username
     * @return 0 验证失败（token不合法） 1 已过期 2 验证成功
     */
    public int verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            DecodedJWT jwt = verifier.verify(token);
            return 2;
        } catch (Exception e) {
            if (isExpire(token) == 1) {
                return 1;
            } else if (isExpire(token) == 0) {
                return 0;
            }
            return 2;
        }
    }

    /**
     * 判断过期
     *
     * @param token token
     */
    public int isExpire(String token) {
        DecodedJWT jwt;
        try {
            jwt = JWT.decode(token);
            // 过期
            if (System.currentTimeMillis() > jwt.getExpiresAt().getTime()) {
                return 1;
            } else {
                return 2;
            }
        } catch (Exception e) {
            return 0;
        }
    }
}
