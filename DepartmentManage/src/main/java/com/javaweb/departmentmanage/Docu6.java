package com.javaweb.departmentmanage;

public class Docu6 {
    /*
    自己动手实战部分：
    1. 班级管理-----》参照部门管理
        要求：班级信息的增删改查----》条件分页查询，参考dept的三层架构

        查（带条件分页查询）
        基本信息
        请求路径：/clazzs
        请求方式：get
        请求参数：queryString
        请求数据样例：/clazzs?name=java&begin=2023-01-018&end= 2023-06-30&page=1&pageSize=5
        响应数据：json格式
        -----》需要有一个clazz的实体类用来封装班级信息

        查询的页面原型：序号、班级名称、班级教室、班主任、开课时间、结课时间、状态、最后操作时间、操作

        删除
        请求路径：/clazzs/{id}--- 》根据id删除班级
        请求方式：delete
        请求参数：id
        请求数据样例：/clazzs/5
        响应数据：json格式
        响应参数：无

        增（添加）
        请求路径：/clazzs
        请求方式：post
        请求参数：json
        请求数据样例：{
            "name":"javaEE就业166期",
            "room":"101",
            "beginDate":"2023-06-01",
            "endDate":"2024-01-20",
            "masterId":1,
            "subject":1
        }
        响应数据：json格式

        根据id查询
        请求路径：/clazzs/{id}--- 》根据id查询班级
        请求方式：get
        请求参数：路径参数
        响应数据：json格式

        修改班级
        请求路径：/clazzs
        请求方式：put
        请求参数：json
        响应数据：json格式

        查询所有
        请求路径：/clazzs/list
        请求方式：get
        请求参数：无
        响应数据：json格式
        响应参数：无

准备工作：
1. 班级管理的实体类：clazz
属性：id、int,name、varchar,room、varchar,begin_date、date,end_date、date,
    master_id、int,subject、tinyint,create_time、datetime,update_time、datetime,

    创建ClazzQueryParam实体类，用来封装班级查询的请求参数
    属性：page、pageSize、name、begin、end

2. 三层架构创建：ClazzController、ClazzService、ClazzMapper



还有一个问题：这里的班级查询功能只完成了分页功能，还没有增加带条件查询功能

     */
    /*
   2. 学员管理-----》参照员工管理
        要求：对学员进行增删改查操作，参考emp的三层架构

        准备工作：student表、三层架构创建
        实体类：student：
        Integer id; String name; String no; Integer gender; //性别 , 1: 男 , 2 : 女
   String phone; String idCard; Integer isCollege; String address; //联系地址
    Integer degree; LocalDate graduationDate; Integer clazzId; //班级ID
    Short violationCount; Short violationScore; LocalDateTime createTime; LocalDateTime updateTime;

    1. 列表查询
    请求路径：/students
    请求方式：get
    请求参数：queryString---->一个分页查询
    响应是数据：json格式
    表结构：student表通过clazz_id字段关联clazz表id字段（逻辑外键）


    2. 删除
    请求路径：/students/{ids}--- 》批量删除
    请求方式：delete
    请求参数：ids
    请求参数样例：/students/1,2,3,4,5
    响应数据：json格式
    响应参数：无

    3. 添加
    请求路径：/students
    请求方式：post
    参数格式：json---》请求参数是一个student对象
    请求数据样例：{
        "name":"张三",
        "no":"20230101",
        "gender":1,
        "phone":"13800000000",
        "idCard":"44030419900101001X",
        "isCollege":1,
        "address":"中国",
        "degree":1,
        "graduationDate":"2023-06-30",
        "clazzId":1,
        "violationCount":0,
        "violationScore":0
    }
    响应数据：json格式
    响应参数：无


    4. 根据id查询
    请求路径：/students/{id}--- 》根据id查询学员
    请求方式：get
    请求参数：id
    响应数据：json格式
    响应参数：json格式

    5. 修改
    请求路径：/students
    请求方式：put
    请求参数：json
    响应数据：json格式
    响应参数：无

    6. 违纪处理
    请求路径：/students/violation/{id}/{score}--- 》根据id和扣分处理违纪
    请求方式：put
    请求参数：id,score
    请求参数样例：/students/violation/1/10
    响应数据：json格式
    响应参数样例：{code：1，msg：success，data：null}
    响应参数：无



     */
    /*
    3. 学员信息统计-----》参照员工信息统计
        要求：统计各个班级的人数-----》柱状图
             统计学员学历分布-----》饼状图
             参考report的三层架构


     1. 学员学历统计
     请求路径：/report/studentDegreeData
     请求方式：get
     请求参数：无
     响应数据：json格式
     响应参数：map集合的键值对---》饼图使用，key为学历，value为人数

     2. 班级人数统计
     请求路径：/report/studentCountData
     请求方式：get
     描述：统计每一个班级的人数
     请求参数：无
     响应数据：json格式
     响应参数：list集合，其中一个clazzlist，一个是dataList
     sql语句：使用的表：clazz、student，student.clazz_id=clazz.id
     select c.name as clazzName,
     count(s.id) as quantity
     from clazz c left join student s on c.id=s.clazz_id
     group by c.name
     */

    /*
    5. 需求：功能完善
- 删除部门时：如果部门下有员工，则不允许删除该部门，并给前端提示错误信息：对不起，当前部门下有员工，不能直接删除！

        直接在DeptServiceImpl中添加业务逻辑判断
        如果部门下有员工，则抛出业务异常
        如果部门下没有员工，则删除部门

        另外：在GlobalExceptionHandler中添加业务异常处理方法
        捕获业务异常类
        新增一个自定义异常类BusinessException，用来处理业务异常：记录错误日志，返回错误信息

     */
}
