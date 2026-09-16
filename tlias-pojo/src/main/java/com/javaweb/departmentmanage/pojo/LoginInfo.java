package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// ログイン成功時のレスポンス結果を格納するエンティティクラス（認証情報保持用）
public class LoginInfo {
    private Integer id; // ログイン社員ID
    private String username; // ユーザー名
    private String name; // 社員氏名
    private String token; // 認証用JWTトークン
}
