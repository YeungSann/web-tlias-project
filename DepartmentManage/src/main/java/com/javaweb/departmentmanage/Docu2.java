package com.javaweb.departmentmanage;

public class Docu2 {
    /*
    第二部分：员工管理
    1. 员工表：
    字段：用户名（2-20）、姓名（2-10）、性别、手机号、职位、薪资、所属部门、入职日期、头像
    工作经历：单独建表


    涉及的表：部门表：dept（id，部门名称，创建时间，修改时间）

    员工表：emp（id，用户名，姓名，性别，手机号，职位，薪资，所属部门，入职日期，头像，创建时间，修改时间）
    id：主键，非负，自增
    username：20位，非空，唯一
    密码：32位，默认123456
    name：10位，非空
    gender：tinyint，1：男， 2：女
    phone：11位非空唯一
    job：tinyint，1：班主任，2讲师， 3学工主管，4教研主任，5咨询师
    salary：int 非负
    image：头像，25位
    entry_date date
    dept_id int，非负
    create_time datetime
    update_time datetime


    1. 准备工作
    创建emp表和emp_expr表
    准备两个表对应的实体类和三层结构

    0. 分页条的设置----》分页查询
    因为表中数据量较大，所以需要分页查询---》这里设置为每页显示10条员工信息

    有两种实现方式：
    先计算分页查询需要从服务器端获取哪些数据？
    1. 是总的记录条数，前端获取到后，根据每页显示的条数，计算出总页数
        查询总记录数：select count(*) from emp;
    2. 是当前页码，前端获取到后，根据每页显示的条数，计算出当前页的起始索引和结束索引
            每页的起始索引 = （当前页码- 1） * 每页显示的条数
            但是这个，通过左外连接直接查询员工信息，并渲染到页面上
        查询语句：select * from emp e left join dept d on e.dept_id = d.id limit ?,?;

    前端传递给后端的分页参数？---------相对应的后端返回的参数及类型
    1. 当前页码 page-------------------数据列表 list rows
    2. 每页显示的条数 pageSize----------总记录数 long total
    为了后续其他分页查询更方便，我们直接把这两个数据封装到一个实体类PageResult中，方便后续调用

   功能一：员工列表查询的要求
   请求路径：emps
   请求方式：GET
   请求参数：queryString
   请求数据样例：/emps?name=张&gender=1&begin=2000-01-01&page=1&pageSize=10
   响应参数格式：application/json

   三层数据：
controller--------------------------》service-------------------------------》mapper
1. 接收参数（分页）               1. 调用mapper，查询总记录数                1. 查询总记录数
2. 调用service，进行             2. 调用mapper，查询结果列表                2. 查询结果列表
    分页查询，获取pageResult
3. 响应结果                     3. 封装pageResult对象，返回

注意点：
Emp实体类中，增加关联的部门名称字段deptName----》用来封装查询到的部门名称


    1. 原始处理方法
        以上就是使用原始的处理方法，分层书写
        其中要注意：mapper层只需要执行两个查询语句
        service实现类中才执行分页查询的逻辑（把mapper层查询的结果封装为一个对象）
        controller层只需要调用service层的方法，返回结果即可


    2. 基于pageHelper插件的分页查询----》国外基本不使用
        PageHelper插件是mybatis框架中用来实现分页的插件，用来简化分页操作、提高开发效率
        流程：
        只需要我们配置查询语句， 分页工作交由pageHelper插件来完成
        在这之前，需要先配置pagehelper的静态方法，用来定义分页参数的名称
        实操：
        1. 导入依赖
        2. 在mapper中直接进行一个查询即可：select * from emp ... 返回的结果是List<Emp>
        3. 在service实现类中，设置分页参数，调用mapper层的方法，然后对接收到的结果解析并封装后返回
            在service层中，因为考虑到我们需要把总记录数、当前页数据返回给controller层，因此需要把
            list对象强制转换为pagehelper接口中的page对象
        4. 最后，在返回值处，直接调用page对象的getTotal()方法和ggetResult()方法，即可获取到总记录数和当前页数据


   3. 国外基于Spring Data JPA进行更简单的分页查询
   实操：
   1. 引入Spring Data JPA的依赖
   2. 在实体类emp上增加Entity注解，通过jpa把emp实体类映射到emp表中
   3. mapper层，中去掉mapper注解，改为使用Repository注解，并继承jpaRepository接口


   停止：jpa跟mybatis同等级的高级框架，这里不使用
        jpa的比mybatis更高级，对增删改查的操作更方便，但是学习成本较高


     */

    /*
    第二部分：条件分页查询
    1. 通过员工名称查询员工列表----支持模糊查询

    2. 选择员工性别进行精确查询

    3. 选择入职时间的 开始时间和 结束时间 ， 可以进行范围查询

注意：以上三个条件是and关系，必须同时满足

    4. 对查询结果根据修改时间 倒序排序，并对查询结果进行分页展示


    实操：
    1. 条件查询语句：这里继续使用pagehelper，因此不需要count总记录数
        1. 姓名模糊查询
    "select e.* ,d.name deptName from emp e left join dept d on " +
            "e.dept_id = d.id where e.name like '%#{name}%' and e.gender = #{gender} " +
            "and e.entry_date between #{begin} and #{end} order by e.update_time desc "

    请求路径：emps
   请求方式：GET
   请求参数：queryString
   请求数据样例：name、gender、begin、end、page、pageSize
   这里，page和pageSize在前面的分页查询中已经定义了。
   至于前面的四个参数，因为请求方式是get，因此还是需要用户在地址栏传入
   响应参数格式：application/json

三层架构：
controller--------------------------》service-------------------------------》mapper
1. 接收参数（分页）               1. 调用mapper中的方法                1. 条件分页查询语句
2. 调用service，进行             2. 使用pagehelper分页查询
    分页查询，获取pageResult
3. 响应结果                     3. 封装pageResult对象，返回


// 条件分页查询，调用service的listPage2方法，响应结果
    // 这里必须要增加一个路径，因为跟上一个方法都是get方式，路径冲突了
    @GetMapping("/list2")
    public Result listPage2(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                            // 增加条件查询中增加的四个参数
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "gender", required = false) Integer gender,
                            // 日期类型的begin和end，统一定义格式，避免用户传入不同格式的日期数据
                            @RequestParam(value = "begin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        // 日志中也增加条件查询增加的四个参数，方便调试
        log.info("条件分页查询：{}，{}，{}，{}，{},{}", page, pageSize, name, gender, begin, end);
        // 调用service的listPage2方法，返回结果列表
        PageResult<Emp> pageResult = empService.listPage2(page, pageSize, name, gender, begin, end);


        return Result.success(pageResult);
    }

    先从controller开始传入另外四个参数
    然后逐级往service层和mapper层传递，最后在mapper层执行查询语句，返回结果列表

     */

    /*
    程序优化

    现有问题：都是使用get方式请求，像第二种条件分页查询，已经需要传入6个参数
    如果将来需要增加更多的条件查询参数，需要在地址栏中继续增加参数，比较麻烦

    解决方案：
    创建一个对象类，用于封装条件查询参数：EmpQueryParam

    创建之后，只需要在controller层中直接传入EmpQueryParam对象即可，其他参数会自动从对象中获取


    程序优化二------条件采用动态sql语句
    实操：只需要更改mapper.xml文件中的sql语句，变为动态sql
    使用<where>、<if 属性名 != null and">标签，来动态拼接sql语句
    注意：
    这里使用where标签的意义：自动去除不必要的and和or关键字，使得sql语法更规范

    具体内容，需要去到mybatis官网中关于映射部分的动态sql语句章节

     */
}
