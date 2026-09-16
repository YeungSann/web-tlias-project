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
    // 現在のログテーブル内の最大ID値を取得するクエリを定義
    @Select("select max(id) from log")
    Integer getMaxId();

    // 引数として受け取った logger オブジェクトをデータベースに挿入するインサート文を定義
    @Insert("insert into log " +
            "(operate_emp_id, operation_time, class_name, method_name, args, result, cost_time) " +
            "values " +
            "(#{operateEmpId}, #{operationTime}, #{className}, #{methodName}, #{args}, #{result}, #{costTime})")
    void insert(Logger logger);

    // 操作ログのページング検索
    // 1. 条件に一致するログの総レコード数を取得
    Long count(LogQueryParam queryParam);

    // 2. 条件に基づくページングデータの取得
    List<Logger> page(@Param("queryParam") LogQueryParam queryParam,
                      @Param("start") Integer start,
                      @Param("pageSize") Integer pageSize);
}
