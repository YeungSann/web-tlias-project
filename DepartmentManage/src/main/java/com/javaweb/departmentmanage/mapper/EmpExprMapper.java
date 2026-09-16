package com.javaweb.departmentmanage.mapper;

import com.javaweb.departmentmanage.pojo.EmpExpr;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    // 社員の職歴情報を一括保存（バッチ挿入）
// 2. 社員の職歴情報を一括保存（バッチ挿入）
    //@Insert("insert into emp_expr (emp_id,begin,end,company,job) values " +
    //      "(#{empId},#{begin},#{end},#{company},#{job})")
    void saveBatchExpr(@Param("exprList") List<EmpExpr> exprList);


    void deleteByEmpIds(@Param("empIds") List<Integer> empIds);



}
