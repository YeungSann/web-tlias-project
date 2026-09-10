package com.javaweb.departmentmanage.service;

import com.javaweb.departmentmanage.pojo.Dept;

import java.util.List;

public interface DeptService {

    // 规定定义一个findAll方法，用于查询全部部门数据
    List<Dept> findAll();

    // 定义一个deleteById方法，用于删除部门
    void deleteById(Integer id);

    void addDept(Dept dept);

    Dept findById(Integer id);

    void updateDept(Dept dept);
}
