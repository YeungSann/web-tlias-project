package com.javaweb.departmentmanage.mapper;


import com.javaweb.departmentmanage.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

// 部门Mapper接口
@Mapper
public interface DeptMapper {
    /*
    // 手动结果映射，把Dept类中的createTime和updateTime字段映射到数据库表中的create_time和update_time字段
    @Results({
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
     */

    // 在sql文中对使用驼峰命名的字段采用起别名的方式进行映射
    // 最优的解决方案是：在yml配置文件中开启驼峰命名开关即可
    // 查询全部部门数据
    @Select("select id, name, create_time, update_time from dept order by update_time desc")
    List<Dept> findAll();


    // 删除部门
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    // 新增部门
    // 预编译语句中，写的必须是封装在实体类中的属性名
    @Insert("insert into dept(name, create_time, update_time) values(#{name}, #{createTime}, #{updateTime})")
    void addDept(Dept dept);

    // 根据id查询部门数据
    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept findById(Integer id);

    // 更新部门数据
    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void updateDept(Dept dept);
}
