package com.javaweb.departmentmanage;

public class Docu5 {
    /*
    异常处理：
    当前端页面执行了增删改操作之后，保存失败时页面没有任何反应-----》因此，操作人员根本不知道
    后台具体的情况是什么

    这个时候，我么需要通过对异常进行处理，然后提示操作人员具体的问题

    小项目：
    直接在controller层中使用try-catch来捕获异常即可

    但是，如果是大型项目，可能有极多个controller
    这时候--------》全局异常处理器
    全局异常处理器：可以接住：从mapper层往上抛出的异常------》最终给出一个异常结果

    实操：
    定义一个全局异常处理类
    handleExceptionHandler
     */

    /*
    员工信息统计功能
    1. 员工职位统计

    2. 员工性别统计


    统计使用阿帕奇的echarts可视化展示插件
    基本写法：
    传入x轴的各列名称
    y轴传入对应的数据----》因此，sql聚合函数得到的数据就是要传给y轴

    基本信息
    请求路径：/report/empJobData
    请求方式：GET
    请求参数：无
    响应数据：json格式

    准备工作：先定义一个对应json响应格式的工具类
    两个属性：x轴的工作类型：jobList
    y轴的数据：dataList


    实操：
    1. 员工职位统计
     sql： select job , count(*) from emp group by job;
     1：教研主管，2：学工主管，3：其他，4：班主任，5：咨询师，6：讲师
     然后通过case表达式把job转换为中文名称
     sql：
     select
     (case job
     when '1' then '班主任'
     when '2' then '讲师'
     when '3' then '学工主管'
     when '4' then '教研主管'
     when '5' then '咨询师'
     else '其他'
     end ) position,
     count(*)  quantity
     from emp group by job

     上面这个写法有局限性：必须要对应上具体的值
     更好的写法：可以实现范围匹配
     sql：
     select
     (case
     when job = 1 then '班主任'
     when job = 2 then '讲师'
     when job = 3 then '学工主管'
     when job = 4 then '教研主管'
     when job = 5 then '咨询师'
     else '其他' end ) position,
     count(*)  quantity
     from emp group by job



    2. 性别统计
    基本信息
    请求路径：/report/empGenderData
    请求方式：GET
    请求参数：无
    响应数据：json格式
    这次对应的是饼状图，还是两个属性：
    一个是性别，一个是性别对应的数据

    定义一个工具类，用于封装性别和对应的数据
    两个属性：name和value

    还是使用list来封装map集合

    sql：
     select
            if(gender = 1 ,'男性员工','女性员工') name,
            count(*) value
        from emp group by gender

    注意：这里不需要再写一个性别的工具类，饼状图需要的本来就是一对一对的键值对
    也就是gender = 男，value = 男的人数
    gender = 女，value = 女的人数
    这样的两对键值对，然后我们再mapper获得这个map之后，直接往上抛就可以

    这里只有男和女两种性别，因此直接使用if语句即可，sql中的if语句类似与java中的三目运算符，true返回前一个值，false返回后一个值

    还有一种是：ifnull( expr, val1)  如果expr为null，返回expr，否则返回val1
     */




}
