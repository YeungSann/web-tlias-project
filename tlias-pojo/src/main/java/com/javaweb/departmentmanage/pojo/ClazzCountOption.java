package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * クラス別人数統計用データモデル（グラフ表示用データ構造）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClazzCountOption {
    private List<String> clazzList = new ArrayList<>(); // クラス名リスト（X軸カテゴリ等）
    private List<Object> dataList = new ArrayList<>(); // 在籍人数リスト（Y軸データ列等）
}
