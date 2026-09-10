package com.javaweb.departmentmanage.filter;

import com.javaweb.departmentmanage.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
//@WebFilter("/*")// 统一拦截所有路径
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 先把传进来的形参转换为HttpServletRequest和HttpServletResponse
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 记录日志，拦截到请求
        log.info("TokenFilter doFilter 拦截到请求");

        // 先判断请求路径是否是登录接口
        if (request.getRequestURI().contains("/login")) {
            // 登录接口，直接放行
            log.info("登录接口，直接放行");
            filterChain.doFilter(request, response);
            return;
        }

        // 非登录接口，先获取用户的token
        String token = request.getHeader("token");
        // 判断token是否为空
        if (token == null || token.isEmpty()) {
            log.info("token为不存在或为空，直接响应401状态码");
            // token为空，直接响应401状态码
            // 或者使用setStatus方法设置401状态码
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "未登录");
            return;
        }

        // token不为空，执行对token的解析---》 调用令牌工具类JwtUtils中的parseToken方法解析token
        try {
            // 解析token，获取用户信息
            JwtUtils.parseToken(token);
        }catch (Exception ex){
            // 解析token失败，直接响应401状态码
            log.info("解析token失败，直接响应401状态码");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 解析token成功，放行
        log.info("解析token成功，放行");
        filterChain.doFilter(request, response);
    }
}
