package com.javaweb.departmentmanage.mapper;

import com.javaweb.departmentmanage.pojo.Clazz;
import com.javaweb.departmentmanage.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClazzMapper {

    // 查询全部班级的总记录数

    long findAll(@Param("queryParam") ClazzQueryParam queryParam);

    // 查询全部班级的分页数据
    // 前端展示：clazz表：班级序号、班级名称、班级教室、开课时间、结课时间、状态、最后操作时间、操作
    // 2. 班级分页查询：关联 emp 表获取班主任姓名
    List<Clazz> findPage(@Param("queryParam") ClazzQueryParam queryParam,
            @Param("start") Integer start,
            @Param("pageSize") Integer pageSize);

    // 根据id删除班级
    @Delete("delete from clazz where id = #{id}")
    void deleteById(Integer id);

    // 添加班级
    @Insert("insert into clazz " +
            "(name, room, begin_date, end_date, master_id, subject, create_time, update_time) " +
            "values " +
            "(#{name}, #{room}, #{beginDate}, #{endDate}, #{masterId}, #{subject}, #{createTime}, #{updateTime})")
    void addClazz(Clazz clazz);

    // 根据id查询班级，为了规范，应该把所有字段都写出来
    @Select("select " +
            "id, name, room, begin_date, end_date, master_id, subject, create_time, update_time " +
            "from clazz where id = #{id}")
    Clazz findById(Integer id);


    // 更新班级信息，因为字段较多，使用mapper的动态sql语句进行更新
    void updateClazz(Clazz clazz);

    // 查询所有班级
    @Select("select * from clazz")
    List<Clazz> getClazzs();
}
