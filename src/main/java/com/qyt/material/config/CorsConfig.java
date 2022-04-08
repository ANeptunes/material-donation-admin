package com.qyt.material.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 设置允许的方法
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE", "HEAD")
                // 跨域允许时间
                .maxAge(3600)
                // 是否允许证书（cookies）
                .allowCredentials(true)
                .allowedHeaders("*");
    }
}
