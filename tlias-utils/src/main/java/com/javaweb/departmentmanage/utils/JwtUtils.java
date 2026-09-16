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
    // JWT（JSON Web Token）操作用のユーティリティクラス（トークンの生成および検証・解析を行う）
    // 署名・検証（暗号化/復号化）に使用する秘密鍵（シークレットキー）を定義する
    private static final String SECRET_KEY = "com/javaweb/departmentmanage/controller/LoginController";
    // Base64/UTF-8エンコードされた秘密鍵文字列から、HMAC-SHAアルゴリズム用のSecretKeyオブジェクトを生成する
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    // トークンの有効期限をミリ秒単位で定義する（ここでは12時間：1000ms * 60s * 60m * 12h）
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 12;

    // JWTトークンを生成するメソッドを定義する
    // 引数として、ユーザー情報（クレーム情報）が格納されたMapを受け取る
    public static String generateToken(Map<String, Object> claims) {
        // 標準規格に基づいたJWTトークンを構築（ビルド）する
        String token = builder()
                // サブジェクト（Subject）にシステム識別子（ユーザーID/システム名等）を設定する
                .subject("Tlias")
                // Mapに格納されたカスタムユーザー情報をペイロード（Payload/Claims）に追加する
                .claims(claims)
                // 現在時刻に有効期限を加算し、トークンの失効日時（Expiration）を設定する
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                // トークンの発行日時（Issued At）を記録する
                .issuedAt(new Date())
                // 指定した秘密鍵（KEY）を用いてデジタル署名を行う
                .signWith(KEY)
                // JWTトークン文字列（Base64URLエンコード）を生成して完了する
                .compact();
        return token;
    }

    // JWTトークンを解析（パース）し、格納されているクレーム情報（Claims）を取得するメソッドを定義する
    public static Claims parseToken(String token) {
        // トークンを検証・解読（解析）する
        Claims body = parser()
                // 署名検証用の秘密鍵を設定し、改ざんがないか検証（復号）する
                .verifyWith(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return body;
    }
}
