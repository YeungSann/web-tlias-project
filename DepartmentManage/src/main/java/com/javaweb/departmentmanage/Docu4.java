package com.javaweb.departmentmanage;

public class Docu4 {
    /*
    删除员工

    需求：
    1. 点击删除按钮后，先显示员工的信息------》查询回调
    2. 然后弹窗提示是否确定删除员工
    3. 如果确定删除，就调用service层的方法，删除员工

    批量删除：
    1. 前面的单选框勾选多个员工
    2. 此时再点击删除按钮，就会删除选中的多个员工

    本质上，我们可以把单个删除行为看作是特殊的批量删除
    这样的话，只需开发一个接口就可以实现删除员工的功能
    --- 》使用动态sql语句即可

    基本信息
    请求路径：emps
    请求方式：DELETE
    请求参数：查询查询----》查询回调
    请求参数样例：emps?ids=1,2,3
    响应数据：json
    响应数据样例：不需要返回数据


    逐步思考：
    1. 先写sql语句
    delete * from emp where id in (1,2,3)
    // 这里要注意emp_expr表中emp_id字段的值，是emp表中id字段的值-------》emp_id与emp.id是逻辑外键关系
    所以我们使用empExpr中的empId字段，来删除员工表达式信息，而不是id字段
    delete * from emp_expr where emp_id in (1,2,3)

    2. controller层：写一个delete方法，用来删除员工

    3. service层：写一个deleteById方法，用来删除员工
        service层中需要调用两个mapper层的方法，分别删除员工基本信息和员工表达式信息
        EmpMapper.deleteById
        EmpExprMapper.deleteByIds
     */
    /*
    编辑功能的实现：

    1. 查询回显功能
    使用外连接进行查询员工信息和工作经历信息

    2. 编辑功能


    先实现查询回显功能
    controller层：写一个getById方法，用来查询员工的信息
    service层中，分别调用empMapper和empExprMapper的方法，查询员工基本信息和员工表达式信息
    要注意：empMapper层中使用id，empExprMapper层中使用empId-----》因为这两个是逻辑外键关系

    基本信息
    请求路径：emps/{id}
    请求方式：GET
    请求参数：id
    响应数据：json
    响应数据样例：不需要返回数据

    xml中的sql语句：
    <!--  使用外连接查询员工信息和工作经历信息  -->
    <select id="selectById" resultType="com.javaweb.departmentmanage.pojo.Emp">
        select e.* ,
            ex.id exId,
            ex.begin exBegin,
            ex.end exEnd,
            ex.company exCompany,
            ex.job exJob
        from emp e left join emp_expr ex on e.id = ex.emp_id
        where e.id = #{id}
    </select>


    但是，这样有个问题：
    如果一个员工有多条工作经历，那么就会返回多条员工信息+多条工作经历信息
    控制台上容易提示报错

    解决：手动结果封装：使用resultMap标签进行封装
     */
    /*
    编辑功能的实现：修改员工信息
    请求路径：/emps
    请求方式：PUT----》requestbody
    请求参数：json

    注意：关于员工工作经历的修改：先删除再添加

    sql语句：
    动态sql语句进行更新
    1. update emp set phone = #{phone}, job = #{job}, salary = #{salary} where id = #{id}
    2. delete from emp_expr where emp_id = #{id}
    3. insert into emp_expr (emp_id, begin, end, company, job) values (#{empId}, #{begin}, #{end}, #{company}, #{job})

    controller：接收put请求数据，调用service层的方法，修改员工信息和工作经历信息
    service：调用empMapper中的updateById方法来修改员工基本信息
    service：调用empExprMapper中的deleteById方法来删除员工表达式信息
    service：调用empExprMapper中的insertBatch方法来添加员工表达式信息
    mapper：分别写一个updateById方法、deleteById方法、insertBatch方法
     */
}
