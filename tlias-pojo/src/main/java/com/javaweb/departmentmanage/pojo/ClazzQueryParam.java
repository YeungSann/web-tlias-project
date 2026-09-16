package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClazzQueryParam {
    // 検索パラメータ属性：page（ページ番号）、pageSize（1ページあたりの件数）、name（クラス名）、begin（検索開始日）、end（検索終了日）
    private Integer page=1;// 現在のページ番号（デフォルト値：1）
    private Integer pageSize=5;// 1ページあたりの表示件数（デフォルト値：5）
    private String name;// クラス名（あいまい検索用キーワード）
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin; // 開講日検索範囲：開始日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end; // 開講日検索範囲：終了日
}
