package com.javaweb.departmentmanage.controller;

import com.javaweb.departmentmanage.pojo.Dept;
import com.javaweb.departmentmanage.pojo.Result;
import com.javaweb.departmentmanage.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 声明一个Slf4j日志注解，用来添加日记记录器log
@Slf4j
// 声明共同的请求路径，下面的方法不需要重复书写路径
@RequestMapping("/depts")
// 声明这是一个请求处理类
@RestController
public class DeptController {
    // 创建一个日志常量对象,并传入本类的字节码对象
    // 更简化的方式，直接在本类上方，声明一个@Slf4j注解，就可以直接使用log对象了
    // private static final Logger log = LoggerFactory.getLogger(DeptController.class);

    //声明service接口
    @Autowired
    private DeptService deptService;

    // 定义一个请求处理方法，返回值是result对象用来确认请求是否成功
    // 指定请求方式
    //@RequestMapping(value = "/depts", method = RequestMethod.GET)
    // 也可以直接使用getmapping指定请求方式和路径
    // 这样写的话，也可以应用在post、delete等其他请求方式上
    @GetMapping
    public Result list(){
        // 改造为使用日志记录器log输出并记录
        log.info("查询全部部门数据");
        //System.out.println("查询全部部门数据");

        //调用deptService中的查询全部方法，查询全部部门数据
        // 并把查询结果封装到一个list集合中
        List<Dept> deptList = deptService.findAll();

        // 代码如果能走到这里，说明查询成功，直接返回成功结果
        return Result.success(deptList);
    }

    // 定义一个请求处理方法，用于删除部门
    @DeleteMapping
    // 一旦声明了requestparam注解，就必须传递id参数，如果不传递，就会报错
    // 之所以必须传参数，是因为内部的required属性默认是true，必须传递参数
    // 可以手动修改为false

    // 而如果@requestparam中的字段名，跟我们请求的形参名是一致的，那么可以直接省略@requestparam的书写
    // 实际业务逻辑中：用来controller层是用来接收前端发来的请求参数的，也就是id编号
    public Result deleteById(@RequestParam(value = "id", required = false) Integer id){
        // 改造为使用日志记录器log输出并记录
        log.info("删除部门:{}",id);//{}表示占位符，用来表示id参数
        //System.out.println("删除部门:"+id);

        // 调用deptService中的删除方法，删除部门
        deptService.deleteById(id);
        // 代码如果能走到这里，说明删除成功，直接返回成功结果
        return Result.success("删除成功");
    }

    /*
    并不推荐

    // 比较原生的办法：使用httpservletrequest获取请求参数id
    @DeleteMapping("/depts")
    public Result deleteById2(HttpServletRequest request){
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        System.out.println("删除部门"+id);

        // 因为删除操作不需要返回额外的数据，因此直接调用result中的无参success方法即可
        return Result.success();
    }
     */


    // 写一个新增部门的方法
    @PostMapping
    // 关键写法：使用@requestbody注解，把前端发送的json字符串，转换为对应的实体类对象，再赋值给controller层中的参数
    public Result addDept(@RequestBody Dept dept){
        // 改造为使用日志记录器log输出并记录
        log.info("新增部门:{}",dept);
        //System.out.println("新增部门:"+dept);

        // 调用deptService中的新增方法，新增部门
        deptService.addDept(dept);
        // 代码如果能走到这里，说明新增成功，直接返回成功结果
        return Result.success("新增部门成功");
    }

    // 写一个根据id查询部门数据的方法
    // 使用{}标识符用来表示路径参数，id就是路径参数的名称
    @GetMapping("/{id}")
    // 必须使用@PathVariable注解，来接收路径参数id
    // 当然，也可以不使用@PathVariable注解，直接使用id参数，但是这样写的话，路径参数的名称必须和形参名一致
    public Result findById(@PathVariable("id") Integer id){
        // 改造为使用日志记录器log输出并记录
        log.info("根据id查询部门数据:{}",id);//{}表示占位符，用来表示id参数
        //System.out.println("根据id查询部门数据:"+id);

        // 调用deptService中的查询方法，根据id查询部门数据
        Dept dept = deptService.findById(id);
        // 代码如果能走到这里，说明查询成功，直接返回成功结果
        return Result.success(dept);
    }

    // 写一个修改部门的方法
    @PutMapping
    // 添加@requestbody注解，把前端发送的json字符串，转换为对应的实体类对象，再赋值给controller层中的参数
       public Result updateDept(@RequestBody Dept dept){
        // 改造为使用日志记录器log输出并记录
        log.info("修改部门:{}",dept);
        //System.out.println("修改部门:"+dept);

        // 已经获取到当前部门的名称，调用service层中的更新方法，完成修改操作
        deptService.updateDept(dept);
        // 代码如果能走到这里，说明修改成功，直接返回成功结果
        return Result.success("修改部门成功");
    }
}
