package com.javaweb.departmentmanage.service.impl;

import com.javaweb.departmentmanage.mapper.StudentMapper;
import com.javaweb.departmentmanage.pojo.PageResult;
import com.javaweb.departmentmanage.pojo.Student;
import com.javaweb.departmentmanage.pojo.StudentQueryParam;
import com.javaweb.departmentmanage.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    // 带条件的分页查询方法
    @Override
    public PageResult<Student> page(StudentQueryParam param) {
        // 先获取总记录数
        long total = studentMapper.getAllLines(param);

        // 设置分页参数
        Integer start = (param.getPage()-1)*param.getPageSize();
        // 执行查询--->把查询的结果封装到list中
        List<Student> list = studentMapper.page(param,start,param.getPageSize());

        // 把total和list封装到PageResult对象中
        // 返回分页结果
        return new PageResult<>(total, list);
    }

    @Override
    public void deleteById(List<Integer> ids) {
        // 直接调用底层mapper的deleteById方法，删除学员信息
        studentMapper.deleteById(ids);
    }

    // 添加学员方法
    @Override
    public void addStudent(Student student) {
        // 先设置创建时间和更新时间为now
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        // 调用底层mapper的addStudent方法，添加学员信息
        studentMapper.addStudent(student);
    }

    // 根据id查询学员信息方法
    @Override
    public Student getById(Integer id) {
        // 直接调用底层mapper的getById方法，根据学员id查询学员信息
        return studentMapper.getById(id);
    }

    @Override
    public void updateStudent(Student student) {
        // 先写好更新时间
        student.setUpdateTime(LocalDateTime.now());
        // 调用底层mapper的updateStudent方法，修改学员信息
        studentMapper.updateStudent(student);
    }

    @Override
    public void violation(Integer id, Integer score) {
        // 现根据学员id查询学员信息
        Student student = studentMapper.getById(id);

        // score和count都是Short包装类，空值时默认null，需要先进行非空判断
        if (student.getViolationCount() == null) {
            student.setViolationCount((short) 0);
        }
        if (student.getViolationScore() == null) {
            student.setViolationScore((short) 0);
        }

        // 前端输入扣分后，更新该学员的违纪次数+1
        student.setViolationCount((short) (student.getViolationCount() + 1));
        // 把学员的扣分更新为原来的分数+输入的分数
        student.setViolationScore((short) (student.getViolationScore() + score));

        // 最后根据学员id更新学员信息
        studentMapper.updateStudent(student);
    }
}
