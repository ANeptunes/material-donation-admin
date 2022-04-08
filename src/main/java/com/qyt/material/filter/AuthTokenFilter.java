package com.qyt.material.filter;

import com.qyt.material.api.TokenVerifyType;
import com.qyt.material.exception.AuthTokenErrorException;
import com.qyt.material.util.RedisUtil;
import com.qyt.material.util.TokenUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.qyt.material.api.TokenVerifyType.*;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@Component
@Order(2)
@WebFilter(urlPatterns = {"/**"})
public class AuthTokenFilter implements Filter {

    @Resource
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Resource
    private TokenUtil tokenUtil;

    @Resource
    private RedisUtil redisUtil;

    private TokenVerifyType tokenVerifyType;

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/user/login", "/user/register", "/user/info")));


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader("Authorization");
        String username = request.getHeader("Username");
        System.out.println("获取的token信息为: " + token);
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        System.out.println("获取的请求路径是: " + path);
        boolean isAllowedPath = ALLOWED_PATHS.contains(path);
        if (isAllowedPath) {
            filterChain.doFilter(request, response);
            return;
        }
        if (token != null) {
            int flag = tokenUtil.verify(token, username);
            if (flag == FAIL) {
                try {
                    throw new AuthTokenErrorException(4003, false, "token信息非法");
                } catch (AuthTokenErrorException e) {
                    resolver.resolveException(request, response, null, e);
                }
            } else if (flag == EXPIRED) {
                try {
                    String cacheToken = (String) redisUtil.get(username);
                    System.out.println("cacheToken:" + cacheToken);
                    if (token.equals(cacheToken)) {
                        String refreshToken = tokenUtil.token(username);
                        System.out.println("refreshToken: " + refreshToken);
                        redisUtil.set(username, refreshToken, 2 * 60 * 60);
                        response.setHeader("refresh_token", refreshToken);
                        response.setHeader("Access-Control-Expose-Headers", "refresh_token");
                    } else {
                        throw new AuthTokenErrorException(4005, false, "登录信息已失效 , 请重新登录");
                    }
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    resolver.resolveException(request, response, null, e);
                }
            } else if (flag == SUCCEESS) {
                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
