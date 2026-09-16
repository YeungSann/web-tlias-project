package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    // id，name，no，gender，phone，idCard，isCollege，address，degree，graduationDate，clazzId，violationCount，violationScore
    private Integer id; // ID（主キー）
    private String name; // 氏名
    private String no; // 学籍番号（受講生番号）
    private Integer gender; // 性別 , 1: 男性 , 2 : 女性
    private String phone; // 携帯電話番号
    private String idCard; // 身分証明書番号（マイナンバー/身分証）
    private Integer isCollege; // 大学・専門学校等からの入学可否, 1: はい, 0: いいえ
    private String address; // 連絡先住所
    private Integer degree; // 最終学歴, 1: 中学校, 2: 高等学校 , 3: 専門学校/短期大学 , 4: 大学(学士) , 5: 大学院(修士) , 6: 大学院(博士)
    private LocalDate graduationDate; // 卒業年月日
    private Integer clazzId; // 所属クラスID
    private Short violationCount; // 規律違反回数
    private Short violationScore; // 規律違反減点数
    private LocalDateTime createTime; // レコード作成日時
    private LocalDateTime updateTime; // レコード更新日時

    private String clazzName;// 所属クラス名（画面表示用属性）
}
