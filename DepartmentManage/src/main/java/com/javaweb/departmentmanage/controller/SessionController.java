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
 * HttpSession演示
 */
@Slf4j
@RestController
public class SessionController {

    //设置Cookie -------- 》 给浏览器设置一个cookie
    @GetMapping("/c1")
    public Result cookie1(HttpServletResponse response){
        // 创建一个cookie对象，并设置name为login_username，value为tliasSystem
        response.addCookie(new Cookie("login_username","tliasSystem")); //设置Cookie/响应Cookie
        return Result.success();
    }

    //获取Cookie -------- 》 从浏览器中获取cookie中的数据
    @GetMapping("/c2")
    public Result cookie2(HttpServletRequest request){
        // 获取浏览器中所有的cookie对象（一个浏览器中可能有多个cookie对象）
        Cookie[] cookies = request.getCookies();
        // 遍历浏览器的所有cookie对象，判断是否存在本类设置的cookie对象
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("login_username")){
                System.out.println("login_username: "+cookie.getValue()); //输出name为login_username的cookie
            }
        }
        return Result.success();
    }



    @GetMapping("/s1")
    public Result session1(HttpSession session){
        log.info("HttpSession-s1: {}", session.hashCode());

        // 存储id值到session中
        session.setAttribute("loginUser", "tom"); //往session中存储数据
        return Result.success();
    }

    @GetMapping("/s2")
    public Result session2(HttpSession session){
        log.info("HttpSession-s2: {}", session.hashCode());

        // 从session中获取id值
        Object loginUser = session.getAttribute("loginUser"); //从session中获取数据
        log.info("loginUser: {}", loginUser);
        return Result.success(loginUser);
    }
}
