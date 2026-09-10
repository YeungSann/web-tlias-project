package com.javaweb.departmentmanage.mapper;

import com.javaweb.departmentmanage.pojo.EmpExpr;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    // 批量保存员工的工作经历信息
// 2. 批量保存员工的工作经历信息
    //@Insert("insert into emp_expr (emp_id,begin,end,company,job) values " +
    //      "(#{empId},#{begin},#{end},#{company},#{job})")
    void saveBatchExpr(@Param("exprList") List<EmpExpr> exprList);


    void deleteByEmpIds(@Param("empIds") List<Integer> empIds);



}
