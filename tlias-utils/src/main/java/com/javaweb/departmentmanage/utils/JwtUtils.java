package com.javaweb.departmentmanage.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.Jwts.parser;

public class JwtUtils {
    // 这是一个jwt令牌操作工具类，用来生成和解析jwt令牌
    // 定义一个密钥，用来加密和解密
    private static final String SECRET_KEY = "com/javaweb/departmentmanage/controller/LoginController";
    // 把base64编码的密钥，得到最终的密钥对象
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    // 定义一个过期时间，单位为毫秒
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 12;

    // 定义一个生成令牌的方法
    // 要求用户传入map，map中包含封装的用户信息
    public static String generateToken(Map<String, Object> claims) {
        // 构建规范的jwt令牌
        String token = builder()
                // 设置subject中的用户id
                .subject("Tlias")
                // 把map中的用户信息，添加到payload中
                .claims(claims)
                // 把过期时间代入
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                // 记录令牌签发时间
                .issuedAt(new Date())
                // 用密钥加密
                .signWith(KEY)
                // 生成令牌
                .compact();
        return token;
    }

    // 定义一个解析令牌获取用户名的方法
    public static Claims parseToken(String token) {
        // 解析令牌
        Claims body = parser()
                // 传入密钥，用来解密令牌
                .verifyWith(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return body;
    }
}
