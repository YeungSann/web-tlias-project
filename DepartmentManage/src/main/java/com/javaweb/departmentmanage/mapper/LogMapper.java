package com.javaweb.departmentmanage.mapper;

import com.javaweb.departmentmanage.pojo.LogQueryParam;
import com.javaweb.departmentmanage.pojo.Logger;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LogMapper {
    // 定义一个查询语句，获取当前表中最大的id值
    @Select("select max(id) from log")
    Integer getMaxId();

    // 定义一个插入语句，把传进来的logger对象，插入到数据库中
    @Insert("insert into log " +
            "(operate_emp_id, operation_time, class_name, method_name, args, result, cost_time) " +
            "values " +
            "(#{operateEmpId}, #{operationTime}, #{className}, #{methodName}, #{args}, #{result}, #{costTime})")
    void insert(Logger logger);

    // 操作日志的分页查询
    // 1. 获取日志总记录数
    Long count(LogQueryParam queryParam);

    // 2. 根据条件进行分页查询
    List<Logger> page(@Param("queryParam") LogQueryParam queryParam,
                      @Param("start") Integer start,
                      @Param("pageSize") Integer pageSize);
}
