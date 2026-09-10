package com.javaweb.departmentmanage.mapper;

import com.javaweb.departmentmanage.pojo.Emp;
import com.javaweb.departmentmanage.pojo.EmpQueryParam;
import com.javaweb.departmentmanage.pojo.JobOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper  {
    // mapper接口层，定义对emp和emp_expr两个表的操作方法

    /*
    // 1. 查询总记录数
    @Select("select count(*) from emp")
    long getTotal();

    // 2. 查询结果列表
    // 给d.name起别名deptName，用来封装查询到的部门名称
    @Select("select e.* ,d.name deptName from emp e left join dept d on " +
            "e.dept_id = d.id order by e.update_time desc limit #{start}, #{pageSize}")
    public List<Emp> findPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize);
     */

    // 查询员工数据
    // 因为自动分页，因此不需要写入起始索引和每页记录数
    // 注意点：使用pagehelper分页查询时，sql语句的末尾绝对不能加;分号。
    // 因为pagehelper会自动给sql语句添加limit语句，如果加了;分号，会导致sql语句语法错误
    /*
    @Select("select e.* ,d.name deptName from emp e left join dept d on " +
            "e.dept_id = d.id order by e.update_time desc")
    public List<Emp> list();


    // 条件分页查询方法
    // 注意，此处的sql语句比较复杂，因此需要采用mapper.xml映射来处理
    //@Select("select e.* ,d.name deptName from emp e left join dept d on " +
    //        "e.dept_id = d.id where e.name like '%#{name}%' and e.gender = #{gender} " +
    //        "and e.entry_date between #{begin} and #{end} order by e.update_time desc ")
    List<Emp> list2(@Param("name") String name, @Param("gender") Integer gender,
                    @Param("begin") LocalDate begin, @Param("end") LocalDate end);
     */

    // 条件分页查询---优化
    List<Emp> list3(EmpQueryParam empQueryParam);


    // 新增员工的两个sql语句---注意第二步需要批量保存员工的工作经历信息---因此第二步需要在empExprMapper中定义
    // 1. 保存员工基本信息
    // 通过options注解，指定使用主键，并把id返回
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp (username,name,gender,phone,job, salary, image, entry_date,dept_id,create_time,update_time) values " +
            "(#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void saveEmp(Emp emp);


    // 删除员工信息
    void deleteByIds(@Param("ids")List<Integer> ids);

    // 查询员工基本信息
    Emp selectById(Integer id);

    // 修改员工基本信息
    void updateById(Emp emp);

    // 统计员工职位人数---》封装到一个map集合中
    List<Map<String,Object>> countJobData();

    // 统计员工性别人数---》封装到一个map集合中
    // 指定map的key为name
    //@MapKey("name")
    // 使用了map作为resultType，mybatis底层会直接封装为一个list<map<>>集合，只需要这个返回值往上抛
    List<Map<String, Object>> countGenderData();

    // 查询全部班主任的信息
    @Select("select id, name from emp where job = 1")
    List<Emp> listMaster();

    // 根据部门id查询部门下是否有员工
    @Select("select count(*) from emp where dept_id = #{id}")
    Long findByDeptId(Integer id);

    // 直接同时通过用户名和密码查询员工信息
    @Select("select * from emp where username = #{username} and password = #{password}")
    Emp selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
