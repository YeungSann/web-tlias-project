package com.javaweb.departmentmanage.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
// @WebFilter("/*")// /* 表示拦截所有请求
public class DemoFilter implements Filter {
    // 初始化方法，只在服务器启动时调用一次
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 记录日志，初始化过滤器
        log.info("初始化过滤器");
    }

    // 拦截到请求之后，会调用本方法，用来处理请求
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 记录日志，拦截到请求
        log.info("拦截到请求");

        // 放行，只能是调用filterChain中的doFilter方法
        // 把本方法中的形参往下传递即可
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // 销毁方法，只在服务器关闭时调用一次
    @Override
    public void destroy() {
        // 记录日志，销毁过滤器
        log.info("销毁过滤器");
    }
}
