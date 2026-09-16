package com.javaweb.departmentmanage.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
//@Component
public class DemoInterceptor implements HandlerInterceptor {
    // ターゲットリソース（Controller）メソッドの実行前に実行される。trueでパス（許可）、falseでインターセプト（遮断）
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle実行前インテーセプト~");
        return true;
    }

    // ターゲットリソース（Controller）メソッドの実行後に実行される
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        log.info("postHandle実行インテーセプト~");
    }

    // ビュー（View）のレンダリング完了後（またはリクエスト処理の最後）に実行される
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.info("afterCompletion実行後インテーセプト~");
    }
}
