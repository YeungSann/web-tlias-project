package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 班级人数统计
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClazzCountOption {
    private List<String> clazzList = new ArrayList<>(); //班级列表
    private List<Object> dataList = new ArrayList<>(); //人数列表
}
