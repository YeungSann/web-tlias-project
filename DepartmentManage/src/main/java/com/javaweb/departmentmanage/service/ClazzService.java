package com.javaweb.departmentmanage.service;

import com.javaweb.departmentmanage.pojo.Clazz;
import com.javaweb.departmentmanage.pojo.ClazzQueryParam;
import com.javaweb.departmentmanage.pojo.PageResult;

import java.util.List;

public interface ClazzService {

    PageResult<Clazz> listPage(ClazzQueryParam queryParam);

    void deleteById(Integer id);

    void addClazz(Clazz clazz);

    Clazz findById(Integer id);

    void updateClazz(Clazz clazz);

    List<Clazz> list();
}
