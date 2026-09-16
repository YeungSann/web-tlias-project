package com.javaweb.departmentmanage.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
//@WebFilter("/*")// /* はすべてのリクエストをインターセプトすることを意味する
public class DemoFilter implements Filter {
    // 初期化メソッド（Webサーバー起動時に1度だけ呼び出される）
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // ログ記録：フィルタの初期化
        log.info("フィルタの初期化");
    }

    // リクエストをインターセプトした際に呼び出され、リクエスト処理を実行するメソッド
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // ログ記録：リクエストをインターセプト
        log.info("リクエストをインターセプトしました");

        // 後続処理（次のフィルタまたはサーブレット）へのパス（放行）。filterChainの doFilter メソッドを呼び出すことで実行
        // 本メソッドの仮引数をそのまま次へ渡す
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // 破棄メソッド（Webサーバー停止・シャットダウン時に1度だけ呼び出される）
    @Override
    public void destroy() {
        // ログ記録：フィルタの破棄
        log.info("フィルタの破棄");
    }
}