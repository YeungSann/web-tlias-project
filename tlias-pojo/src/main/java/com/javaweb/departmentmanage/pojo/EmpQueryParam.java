package com.javaweb.departmentmanage.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpQueryParam {
    private Integer page=1;// 現在のページ番号（デフォルト値：1）
    private Integer pageSize=10;// 1ページあたりの表示件数（デフォルト値：10）
    private String name;// 社員氏名（あいまい検索用キーワード）
    private String gender;// 性別
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;// 入社年月日検索範囲：開始日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;// 入社年月日検索範囲：終了日
}
