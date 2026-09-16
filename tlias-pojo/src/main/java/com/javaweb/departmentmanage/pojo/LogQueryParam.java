package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogQueryParam {
    private Integer page = 1; // 現在のページ番号（デフォルト値：1）
    private Integer pageSize = 10; // 1ページあたりの表示件数（デフォルト値：10）
    private String operatorName; // 操作者名（検索キーワード）
    private String className; // クラス名（完全修飾クラス名での検索キーワード）
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginTime; // 操作日時検索範囲：開始日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime; // 操作日時検索範囲：終了日
}
