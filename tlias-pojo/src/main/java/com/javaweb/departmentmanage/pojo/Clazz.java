package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clazz {
    private Integer id; // ID（主キー）
    private String name; // クラス名（学級名）
    private String room; // 教室名 / 教室番号
    private LocalDate beginDate; // 開講日（授業開始日）
    private LocalDate endDate; // 修了日（授業終了日）
    private Integer masterId; // 担任ID（担任講師・主任の従業員ID）
    private Integer subject; // 学科 / 専攻（コース）
    private LocalDateTime createTime; // レコード作成日時
    private LocalDateTime updateTime; // レコード更新日時

    private String masterName; // 担任氏名（画面表示用拡張属性）
    private String status; // クラス状態（未開講 , 受講中 , 修了）
}
