package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * ページング検索結果ラップ用クラス（汎用レスポンス構造体）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private long total;// 総レコード件数（全データ数）
    private List<T> rows; // 現在のページのデータリスト
}
