package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOption {

    public List<String> jobList = new ArrayList<>(); // 役職名リスト（グラフのX軸カテゴリ等）
    public List<Object> dataList = new ArrayList<>(); // 役職別人数リスト（グラフのY軸数值列等）
}
