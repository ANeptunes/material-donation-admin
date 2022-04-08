package com.qyt;

import com.qyt.material.util.MD5Util;
import com.qyt.material.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTest {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private MD5Util md5Util;


    @Test
    public void test() {
        redisUtil.set("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6ImU5MDFmYTc4OTFlZDU4Y2U3YjBjM2E2NzY4NmQ0NThjIiwiZXhwIjoxNjQyODE1OTMzLCJ1c2VybmFtZSI6InVzZXIwMSJ9.f-pul6YvsWSkSUJUjDmnI1AG3rswmX052wzvTdB-YXc");
        System.out.println(redisUtil.get("token"));

        System.out.println(md5Util.md5Encryption("1234"));
    }
}
