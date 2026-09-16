package com.javaweb.departmentmanage.service;

import com.javaweb.departmentmanage.pojo.Dept;

import java.util.List;

public interface DeptService {

    // 全部署データを取得するための findAll メソッドを定義
    List<Dept> findAll();

    // 部署を削除するための deleteById メソッドを定義
    void deleteById(Integer id);

    void addDept(Dept dept);

    Dept findById(Integer id);

    void updateDept(Dept dept);
}
