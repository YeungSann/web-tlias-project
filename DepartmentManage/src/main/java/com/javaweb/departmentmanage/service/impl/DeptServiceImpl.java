package com.javaweb.departmentmanage.service.impl;

import com.javaweb.departmentmanage.exception.BusinessException;
import com.javaweb.departmentmanage.mapper.DeptMapper;
import com.javaweb.departmentmanage.mapper.EmpMapper;
import com.javaweb.departmentmanage.pojo.Dept;
import com.javaweb.departmentmanage.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// 创建service层的实现类
// 交给Spring管理的Bean
@Service
public class DeptServiceImpl implements DeptService {
    // 注入mapper接口的实现类对象
    @Autowired
    private DeptMapper deptMapper;
    // 注入员工的mapper层对象
    @Autowired
    private EmpMapper empMapper;

    // 实现父接口的findAll方法，用于查询全部部门数据
    @Override
    public List<Dept> findAll() {
        // 调用底层的mapper接口方法查询全部部门数据
        return deptMapper.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        // 在执行删除逻辑之前，先判断部门下是否有员工
        // 如果有员工，则不允许删除该部门，并给前端提示错误信息：对不起，当前部门下有员工，不能直接删除！
        if (empMapper.findByDeptId(id) != 0L && deptMapper.findById(id) != null) {
            throw new BusinessException("对不起，当前部门下有员工，不能直接删除！");
        }

            // 调用底层mapper接口中的删除方法，并把删除的行数返回
            deptMapper.deleteById(id);
    }

    @Override
    public void addDept(Dept dept) {
        // 调用底层mapper接口中的新增方法，新增部门
        // 新增部门时，需要设置部门的创建时间和更新时间
        // 直接设为当前时间即可，这样就可以达到，只需要前端输入部门名称，自动填充时间的效果
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        // 调用底层mapper接口中的新增方法，新增部门
        deptMapper.addDept(dept);
    }

    @Override
    public Dept findById(Integer id) {
        // 调用底层mapper接口中的查询方法，根据id查询部门数据
        return deptMapper.findById(id);
    }

    @Override
    public void updateDept(Dept dept) {
        // 更新部门时，需要自动设置部门的更新时间为当前时间
        dept.setUpdateTime(LocalDateTime.now());
        // 调用底层mapper接口中的更新方法，更新部门数据
        deptMapper.updateDept(dept);
    }
}
