package com.javaweb.departmentmanage.mapper;

import com.javaweb.departmentmanage.pojo.Student;
import com.javaweb.departmentmanage.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    // 查询总记录数
    long getAllLines(@Param("param") StudentQueryParam param);

    // 分页查询
    // 查询字段：姓名、学号、班级、性别、手机号、最高学历、违纪次数、违纪扣分、最后操作时间、操作
    // 把service层定义的start往下传给mapper层
    List<Student> page(@Param("param") StudentQueryParam param,
                       @Param("start") Integer start,
                       @Param("pageSize") Integer pageSize);

    // 根据ids批量删除学员
    void deleteById(@Param("ids") List<Integer> ids);

    // 添加学员
    //id，name，no，gender，phone，idCard，isCollege，address，degree，graduationDate，clazzId，violationCount，violationScore
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into student" + 
            "(name,no,gender,phone,id_card,is_college,address,degree,graduation_date," +
            "clazz_id,create_time,update_time) " +
            "values(#{name},#{no},#{gender},#{phone},#{idCard},#{isCollege}," +
            "#{address},#{degree},#{graduationDate},#{clazzId}," +
            "#{createTime},#{updateTime})")
    void addStudent(Student student);

    // 根据id查询学员信息
    @Select("select * from student where id=#{id}")
    Student getById(Integer id);

    // 使用动态sql修改学员信息
    void updateStudent(Student student);

    // 统计学员学历分布---》type为map
    List<Map<String, Object>> countDegreeData();

    // 统计班级人数分布---》type为map
    List<Map<String, Object>> countCountData();
}
