package com.javaweb.departmentmanage.filter;

import com.javaweb.departmentmanage.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
//@WebFilter("/*")// 全パスを統一してインターセプトする設定
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // まず受け取った仮引数を HttpServletRequest および HttpServletResponse にダウンキャスト（型変換）する
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // ログ記録：リクエストをインターセプト
        log.info("TokenFilter doFilter リクエストをインターセプトしました");

        // リクエストURIがログインAPI（/login）であるか判定
        if (request.getRequestURI().contains("/login")) {
            // ログインAPIの場合は、そのまま後続処理へパス（放行）する
            log.info("ログインAPIのため、そのままパスします");
            filterChain.doFilter(request, response);
            return;
        }

        // ログインAPI以外の場合、リクエストヘッダーからユーザーのTokenを取得
        String token = request.getHeader("token");
        // Tokenが存在しない、または空であるか判定
        if (token == null || token.isEmpty()) {
            log.info("tokenが存在しないか空のため、直接401ステータスコードを返却します");
            // Tokenが空の場合、直接401（SC_UNAUTHORIZED）ステータスコードをレスポンスする
            // または setStatus メソッドを使用して401ステータスコードを設定することも可能
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "未認証（未ログイン）");
            return;
        }

        // Tokenが空でない場合、Tokenの解析（検証）を実行---＞ トークンユーティリティクラス JwtUtils の parseToken メソッドを呼び出して検証
        try {
            // Tokenを解析し、ユーザー情報を取得・検証する
            JwtUtils.parseToken(token);
        }catch (Exception ex){
            // Tokenの解析・検証に失敗した場合、直接401ステータスコードをレスポンスする
            log.info("tokenの解析に失敗したため、直接401ステータスコードを返却します");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Tokenの解析・検証に成功した場合、後続処理へパス（放行）する
        log.info("tokenの解析に成功しました。パスします");
        filterChain.doFilter(request, response);
    }
}