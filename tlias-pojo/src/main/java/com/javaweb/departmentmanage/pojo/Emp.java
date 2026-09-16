package com.javaweb.departmentmanage.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp {

    private Integer id; // ID（主キー）
    private String username; // ユーザー名（ログインアカウント）
    private String password; // パスワード
    private String name; // 社員氏名
    private Integer gender; // 性別（1:男性, 2:女性）
    private String phone; // 携帯電話番号
    private Integer job; // 役職（1:クラス担任, 2:講師, 3:学務主任, 4:教研主任, 5:カウンセラー）
    private Integer salary; // 給与（基本給/月給）
    private String image; // プロフィール画像URL（アバター画像）
    private LocalDate entryDate; // 入社年月日
    private Integer deptId; // 関連所属部署ID
    private LocalDateTime createTime; // レコード作成日時
    private LocalDateTime updateTime; // レコード更新日時

    // 関連付けられた所属部署名（画面表示用拡張属性）
    private String deptName;

    // 関連付けられた職歴情報リスト（1対多リレーション）
    private List<EmpExpr> exprList;
}
