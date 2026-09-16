package com.javaweb.departmentmanage.controller;

import com.javaweb.departmentmanage.pojo.Result;
import com.javaweb.departmentmanage.pojo.Result;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * HttpSessionの動作確認・デモ用コントローラー
 */
@Slf4j
@RestController
public class SessionController {

    // Cookieの設定 -------- 》 ブラウザに対してCookieを設定・レスポンスする
    @GetMapping("/c1")
    public Result cookie1(HttpServletResponse response){
        // Cookieオブジェクトを生成し、nameを "login_username"、valueを "tliasSystem" に設定
        response.addCookie(new Cookie("login_username","tliasSystem")); // Cookieの設定/レスポンス
        return Result.success();
    }

    // Cookieの取得 -------- 》 ブラウザから送信されたCookieデータを取得する
    @GetMapping("/c2")
    public Result cookie2(HttpServletRequest request){
        // ブラウザから送信されたすべてのCookieオブジェクトを取得（1つのブラウザ内に複数のCookieが存在する可能性があるため）
        Cookie[] cookies = request.getCookies();
        // すべてのCookieオブジェクトをループ処理し、本クラスで設定した対象のCookieが存在するか判定
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("login_username")){
                System.out.println("login_username: "+cookie.getValue()); // nameが "login_username" であるCookieの値を出力
            }
        }
        return Result.success();
    }



    @GetMapping("/s1")
    public Result session1(HttpSession session){
        log.info("HttpSession-s1: {}", session.hashCode());

        // ID（またはユーザー情報）をセッションに保存
        session.setAttribute("loginUser", "tom"); // セッションにデータを格納
        return Result.success();
    }

    @GetMapping("/s2")
    public Result session2(HttpSession session){
        log.info("HttpSession-s2: {}", session.hashCode());

        // セッションからID（またはユーザー情報）を取得
        Object loginUser = session.getAttribute("loginUser"); // セッションからデータを取得
        log.info("loginUser: {}", loginUser);
        return Result.success(loginUser);
    }
}
