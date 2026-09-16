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
@Component
public class TokenInterceptor implements HandlerInterceptor {
    // まずインターフェースの3つのメソッドを実装する（ここではpreHandleのみオーバーライド）
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("リクエストが受信されました");
        /*
        // まずURIのリクエストパスがログインAPIであるか判定
        if (request.getRequestURI().contains("/login")) {
            log.info("登录接口，放行");
            return true;
        }*/
        // リクエストヘッダーからtokenを取得
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            log.info("tokenが空です，アクセス拒否");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ユーザー未ログイン，アクセス拒否！");
            return false;
        }
        // JwtUtilsユーティリティクラスを呼び出してtokenを解析・検証
        try {
            // tokenを解析し、JSON内のクレーム（Claims）データを取得
            Claims claims = JwtUtils.parseToken(token);

            // JSONデータから社員IDを取得し、BaseContextクラスのsetメソッドを介してThreadLocalに保存する
            Integer id = (Integer) claims.get("id");
            BaseContext.setCurrentEmpId(id);
        } catch (Exception e) {
            log.info("トークンの解析に失敗しました");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // トークンの解析に成功、リクエストをパス（許可）する
        log.info("トークンの解析に成功しました，アクセスされました");

        // リクエスト許可後、ThreadLocal内のリソース（メモリ）を解放する
        // BaseContext.clearCurrentEmpId();
        // IDを保存するため、コメントアウトする
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // リクエストが完了後、ThreadLocal内のリソース（メモリ）を解放する
        BaseContext.clearCurrentEmpId();
    }
}
