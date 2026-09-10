package com.javaweb.departmentmanage.controller;

import com.javaweb.departmentmanage.pojo.Clazz;
import com.javaweb.departmentmanage.pojo.ClazzQueryParam;
import com.javaweb.departmentmanage.pojo.PageResult;
import com.javaweb.departmentmanage.pojo.Result;
import com.javaweb.departmentmanage.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    // 定义一个查询全部班级的方法，请求为get， 返回一个clazz的list
    // 要求：带条件的分页查询
    @GetMapping
    public Result listPage(ClazzQueryParam queryParam){
        // 先记录日志开始
        log.info("开始查询班级信息：{}",queryParam);
        // 调用service层方法查询班级信息，返回为pageresult对象
        PageResult<Clazz> pageResult = clazzService.listPage(queryParam);

        return Result.success(pageResult);
    }

    // 定义一个根据id删除班级的方法，请求方式为delete，请求参数为id
    // :\d+表示id必须是纯数字，解决与下方的list路径的冲突
    @DeleteMapping("/{id:\\d+}")
    public Result delete(@PathVariable("id") Integer id){
        // 先记录日志开始
        log.info("开始删除班级信息：{}",id);
        clazzService.deleteById(id);
        return Result.success();
    }

    // 定义一个添加班级的方法，请求方式post，请求参数为clazz对象
    @PostMapping
    public Result addClazz(@RequestBody Clazz clazz){
        // 先记录日志开始
        log.info("开始添加班级信息：{}",clazz);
        clazzService.addClazz(clazz);
        return Result.success();
    }

    // 定义一个根据id查询班级的方法，请求方式get，请求参数为id
    @GetMapping("/{id:\\d+}")
    public Result findById(@PathVariable("id") Integer id){
        // 先记录日志开始
        log.info("开始根据id查询班级信息：{}",id);
        // 根据id查询到clazz对象
        Clazz clazz = clazzService.findById(id);
        return Result.success(clazz);
    }

    // 定义一个修改班级的方法，请求方式put，请求参数为clazz对象
    @PutMapping
    public Result updateClazz(@RequestBody Clazz clazz){
        // 先记录日志开始
        log.info("开始修改班级信息：{}",clazz);
        clazzService.updateClazz(clazz);
        return Result.success();
    }

    // 定义一个/list路径的get请求，返回一个clazz的list
    @GetMapping("/list")
    public Result list(){
        // 先记录日志开始
        log.info("开始查询班级信息");
        // 调用service层方法查询班级信息，返回为clazz的list
        List<Clazz> clazzList = clazzService.list();
        return Result.success(clazzList);
    }
}
