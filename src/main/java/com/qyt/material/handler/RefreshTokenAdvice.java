//package com.qyt.material.handler;
//
//import com.qyt.material.util.RedisUtil;
//import com.qyt.material.util.TokenUtil;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.http.server.ServletServerHttpResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@RestControllerAdvice
//public class RefreshTokenAdvice implements ResponseBodyAdvice {
//
//    @Resource
//    private RedisUtil redisUtil;
//
//    @Resource
//    private TokenUtil tokenUtil;
//
//    @Override
//    public boolean supports(MethodParameter methodParameter, Class aClass) {
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
//        HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
//        HttpServletResponse response = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse();
//        String token = request.getHeader("Authorization");
//        String username = request.getHeader("Username");
//        String cacheToken = (String) redisUtil.get(username);
//        if (token != null) {
//            if (token.equals(cacheToken)) {
//                String refreshToken = tokenUtil.token(username);
//                System.out.println("refreshToken" + refreshToken);
//                response.addHeader("refreshToken", refreshToken);
//                response.setHeader("Access-Control-Expose-Headers", "refreshToken");
//            }
//        }
//        return o;
//    }
//}
