package com.javaweb.departmentmanage.controller;

import com.javaweb.departmentmanage.pojo.Emp;
import com.javaweb.departmentmanage.pojo.EmpQueryParam;
import com.javaweb.departmentmanage.pojo.PageResult;
import com.javaweb.departmentmanage.pojo.Result;
import com.javaweb.departmentmanage.service.DeptService;
import com.javaweb.departmentmanage.service.impl.EmpServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

// 引入日志记录器
@Slf4j
// 声明公共路径为/emps
@RequestMapping("/emps")
// emp的controller层，用来处理emp相关的请求
@RestController
public class EmpController {
    // 声明下一层级的service对象，这样才能获取到service层的bean类
    @Autowired
    private EmpServiceImpl empService;
/*
    // 使用get请求，调用service的listPage方法，返回结果列表
    @GetMapping
    // 通过@RequestParam注解获取请求参数，page默认值为1，pageSize默认值为5
    // 如果没有传递page参数，默认值为1，没有传递pageSize参数，默认值为5
    public Result listPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        // 日志
        log.info("分页查询：{}，{}", page, pageSize);
        // 调用service的getListPage方法，返回结果列表
        PageResult<Emp> pageResult = empService.getListPage(page, pageSize);
        return Result.success(pageResult);
    }

    // 条件分页查询，调用service的listPage2方法，响应结果
    // 这里必须要增加一个路径，因为跟上一个方法都是get方式，路径冲突了
    @GetMapping
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
 */

    // 条件分页查询，调用service的listPage2方法，响应结果
    // 这里必须要增加一个路径，因为跟上一个方法都是get方式，路径冲突了
    @GetMapping
    public Result listPage3(EmpQueryParam  empQueryParam){
        // 日志中也增加条件查询增加的四个参数，方便调试
        log.info("条件分页查询：{}", empQueryParam);
        // 调用service的listPage2方法，返回结果列表
        PageResult<Emp> pageResult = empService.listPage3(empQueryParam);

        return Result.success(pageResult);
    }

    // 写一个post请求的save方法，用来保存员工信息和工作经历信息
    @PostMapping
    // 通过@RequestBody注解获取请求体中的json数据，将其转换为emp对象
    public Result save(@RequestBody Emp emp)  {
        // 日志中也增加员工对象
        log.info("保存员工信息：{}", emp);
        // 调用service的save方法，返回结果
        empService.save(emp);
        return Result.success();
    }

    // 写一个delete请求的delete员工方法，需要通过查询回显，显示员工的信息
    @DeleteMapping
    // 添加@RequestParam注解，获取请求参数中的ids列表
    public Result deleteById(@RequestParam("ids") List<Integer> ids) {
        // 先显示员工的信息
        log.info("删除员工信息：{}", ids);
        // 调用service的deleteById方法，删除员工
        empService.deleteById(ids);
        return Result.success();
    }


    // 写一个查询员工信息方法，使用get请求，获取员工的id，通过id先查询员工的信息
    @GetMapping("/{id}")
    // id是路径参数，必须要添加@PathVariable注解，才能获取到id的值
    public Result getById(@PathVariable("id") Integer id) {
        // 先查询员工的信息
        log.info("编辑员工信息：{}", id);
        // 调用service的getById方法，查询员工信息
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }

    // 写一个put请求的updateById方法，用来修改员工信息和工作经历信息
    @PutMapping
    // 通过@RequestBody注解获取请求体中的json数据，将其转换为emp对象
    public Result updateById(@RequestBody Emp emp) {
        // 日志中也增加员工对象
        log.info("修改员工信息：{}", emp);
        // 调用service的updateById方法，返回结果
        empService.updateById(emp);
        return Result.success();
    }


    // 追加一个get请求查询全部员工的方法
    @GetMapping("/list")
    public Result listMaster(){
        log.info("查询全部班主任信息");
        // 调用service的listMaster方法，返回结果列表
        List<Emp> list = empService.listMaster();
        return Result.success(list);
    }

}
