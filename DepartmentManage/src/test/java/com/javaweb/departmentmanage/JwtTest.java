package com.javaweb.departmentmanage;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.jsonwebtoken.Jwts.parser;

public class JwtTest {

    // 定义一个生成jwt令牌的测试方法
    @Test
    public void testGenerateJwt() {
        // 生成jwt令牌
        // 教程中的方法：先建一个map，存储需要放到jwt中的json信息
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id","1");
        dataMap.put("name","张三");

        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "Y29tLkphdmFXZWIuZGVwYXJ0bWVudC5UbGlhc1N5c3RlbQo=")
                // 设置签名算法和TliasSystem经过base64编码后的密钥字符串，新版的jwt要求密钥字符串必须要大于32位
                                   .addClaims(dataMap)// 添加需要放到jwt中的json信息
                                   .setExpiration(new Date(System.currentTimeMillis() + 1000 * 30))// 设置令牌的有效使用时间，30秒过期
                                   .compact();// 生成jwt令牌
        System.out.println(jwt);


        /*
        String jwt = Jwts.builder()
                .setSubject("1234567890")// 使用了jwt官方推荐的subject字段，来存储用户id
                .setClaims(Map.of("name", "John Doe", "admin", true))// 添加需要放到jwt中的json信息
                .signWith(SignatureAlgorithm.HS256, "Y29tLkphdmFXZWIuZGVwYXJ0bWVudC5UbGlhc1N5c3RlbQo=")// 设置签名算法和密钥字符串
                .compact();*/

    }


    // 更新的jwt令牌生成：要求用户必须传入一个密钥对象来实现签名的生成
    @Test
    public void testGenerateJwtModern() {
        // 1. 密钥准备：将符合长度（≥32字节）的字符串转化为安全的 SecretKey 对象
        // 使用 Keys.hmacShaKeyFor，它会自动校验密钥长度是否符合 HMAC-SHA256 的安全要求
        String secretString = "Y29tLkphdmFXZWIuZGVwYXJ0bWVudC5UbGlhc1N5c3RlbQo=";
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));

        // 2. 准备自定义业务数据（Payload）
        Map<String, Object> customClaims = Map.of(
                "name", "张三",
                "role", "admin"
        );

        // 3. 构建规范的 JWT
        String jwt = Jwts.builder()
                // 设置标准字段 sub (Subject)：存放用户唯一标识（如用户ID）
                .subject("1")

                // 批量添加自定义业务字段
                .claims(customClaims)

                // 新版规范 API：代替 setExpiration
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1小时过期
                .issuedAt(new Date()) // 规范做法：同时记录签发时间 (iat)

                // 新版规范 API：直接传入 Key 对象，自动推导算法（无需显式写 SignatureAlgorithm.HS256）
                .signWith(key)

                .compact();

        System.out.println(jwt);
    }

    // 声明本测试方法无效
    @Disabled("token过期，暂停单元测试")
    // 解析jwt令牌：要求用户必须传入一个密钥对象来实现签名的验证
    @Test
    public void testParseJwtModern() {
        // 1. 密钥准备：将符合长度（≥32字节）的字符串转化为安全的 SecretKey 对象
        // 使用 Keys.hmacShaKeyFor，它会自动校验密钥长度是否符合 HMAC-SHA256 的安全要求
        String secretString = "Y29tLkphdmFXZWIuZGVwYXJ0bWVudC5UbGlhc1N5c3RlbQo=";
        // 如果是base64编码的密钥字符串，需要使用decoders解码器来解码，而不是标准解码器
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));

        // 使用utf-8 编码，解析secretString，获取新的密钥对象
        SecretKey newKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));

        // 传入之前生成的jwt令牌，来验证签名是否正确
        // 这个令牌设置了30秒过期时间，30秒过后，令牌就会过期无法解析
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi5byg5LiJIiwiaWQiOiIxIiwiZXhwIjoxNzg3MTI1NzkzfQ.tJIwpRM5xCF7-MWwyQ1yE1EMmbgkuXLeAipU9I9QXQg";

        // 一个新的，加密方式不同的令牌
        String newToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwicm9sZSI6ImFkbWluIiwibmFtZSI6IuW8oOS4iSIsImV4cCI6MTc4NzEyOTQ2NiwiaWF0IjoxNzg3MTI1ODY2fQ.a_jJK5ZmGhwn0VoG9jwbU_lPWxiBtyhyglZreYBRKQsTIIrxQ3it_KXN0F4zoI75";

        // 解析令牌,结果得到的claims就是加密之前的json信息
        Claims claims = Jwts.parser()
                .verifyWith(newKey) // 指定密钥
                .build()
                .parseClaimsJws(newToken) // 解析jwt令牌
                .getBody(); // 获取解析后的claims对象

        System.out.println(claims);
    }
}
