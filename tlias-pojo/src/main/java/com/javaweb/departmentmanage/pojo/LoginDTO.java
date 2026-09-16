package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ログインリクエスト用のDTOクラス（ログインリクエストのリクエストパラメータを保持する）
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private String username; // ユーザー名（アカウント）
    private String password; // パスワード
}
