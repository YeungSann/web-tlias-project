package com.javaweb.departmentmanage.service;

import com.javaweb.departmentmanage.pojo.PageResult;
import com.javaweb.departmentmanage.pojo.Student;
import com.javaweb.departmentmanage.pojo.StudentQueryParam;

import java.util.List;

public interface StudentService {
    PageResult<Student> page(StudentQueryParam param);

    void deleteById(List<Integer> ids);

    void addStudent(Student student);

    Student getById(Integer id);

    void updateStudent(Student student);

    void violation(Integer id, Integer score);
}
