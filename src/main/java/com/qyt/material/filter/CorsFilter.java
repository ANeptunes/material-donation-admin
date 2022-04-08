package com.qyt.material.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@Component
@Order(1)
@WebFilter(urlPatterns = {"/**"})
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //根据请求头设置响应头
        String allowOrigin = request.getHeader("Origin");
        System.out.println(allowOrigin);
        String allowHeader = request.getHeader("Access-Control-Request-Headers");
        System.out.println(allowHeader);
        //设置允许跨域的配置
        // 这里填写你允许进行跨域的主机ip 配置为*（正式上线时可以动态配置具体允许的域名和IP）
        response.setHeader("Access-Control-Allow-Origin", allowOrigin);
        // 允许的访问方法
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        response.setHeader("Access-Control-Max-Age", "3600");
        // "token,Origin, X-Requested-With, Content-Type, Accept"
        response.setHeader("Access-Control-Allow-Headers", allowHeader);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
