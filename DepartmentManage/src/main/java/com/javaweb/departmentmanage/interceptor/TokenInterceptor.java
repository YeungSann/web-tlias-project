package com.javaweb.departmentmanage.interceptor;

import com.javaweb.departmentmanage.utils.BaseContext;
import com.javaweb.departmentmanage.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
//@Component
public class TokenInterceptor implements HandlerInterceptor {
    // 先实现三个接口方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截到请求");
        /*
        // 先判断uri的请求路径是否是登录接口
        if (request.getRequestURI().contains("/login")) {
            log.info("登录接口，放行");
            return true;
        }*/
        // 获取请求头中的token
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            log.info("token为空，拒绝访问");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "用户未登录，拒绝访问！");
            return false;
        }
        // 调用jwtutils工具类对token进行解析
        try {
            // 解析token，获取json中的数据
            Claims claims = JwtUtils.parseToken(token);

            // 从json中获取员工id，并通过BaseContext类中的set方法，保存到threadlocal中
            Integer id = (Integer) claims.get("id");
            BaseContext.setCurrentEmpId(id);
        } catch (Exception e) {
            log.info("令牌解析失败");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 令牌解析成功，放行请求
        log.info("令牌解析成功，放行请求");

        // 放行之后，释放threadlocal中的资源
        BaseContext.clearCurrentEmpId();
        return true;
    }

}
