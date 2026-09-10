package com.javaweb.departmentmanage.controller;

import com.javaweb.departmentmanage.pojo.PageResult;
import com.javaweb.departmentmanage.pojo.Result;
import com.javaweb.departmentmanage.pojo.Student;
import com.javaweb.departmentmanage.pojo.StudentQueryParam;
import com.javaweb.departmentmanage.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    // 先注入下一层级的StudentService
    @Autowired
    private StudentService studentService;

    // 写一个分页查询方法，传入参数是StudentQueryParam，返回值是PageResult<Student>
    @GetMapping
    public Result page(StudentQueryParam param) {
        // 日志记录
        log.info("page param: {}", param);
        // 封装为一个pageresult对象
        PageResult<Student> pageResult = studentService.page(param);
        // 返回成功结果
        return Result.success(pageResult);
    }

    // 写一个根据id删除学员的方法，请求方式为delete，请求参数为id
    // 改为使用ids集合批量删除学员信息
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable("ids") List<Integer> ids) {
        // 先记录日志开始
        log.info("开始删除学员信息：{}",ids);
        // 调用service层的deleteById方法，删除学员
        studentService.deleteById(ids);
        // 返回成功结果
        return Result.success();
    }

    // 写一个添加学员信息的方法，post方式，请求参数是一个student对象
    @PostMapping
    public Result add(@RequestBody Student student){
        // 先记录日志开始
        log.info("开始添加学员信息：{}",student);
        // 调用service层的addStudent方法，添加学员
        studentService.addStudent(student);
        // 返回成功结果
        return Result.success();
    }

    // 根据id查询学员信息，get方式，请求参数为id，响应参数是result封装的student对象
    @GetMapping("/{id:\\d+}")
    public Result get(@PathVariable("id") Integer id){
        // 先记录日志开始
        log.info("开始根据学员id查询学员信息：{}",id);
        // 调用service层的getById方法，根据学员id查询学员信息
        Student student = studentService.getById(id);
        // 返回成功结果
        return Result.success(student);
    }

    // 写一个修改学员信息的方法，请求方式put，请求参数是一个student对象
    @PutMapping
    public Result update(@RequestBody Student student){
        // 先记录日志开始
        log.info("开始修改学员信息：{}",student);
        // 调用service层的updateStudent方法，修改学员信息
        studentService.updateStudent(student);
        // 返回成功结果
        return Result.success();
    }

    // 写一个违纪处理方法，请求路径/violation/{id}/{score}
    // 请求方式put，请求参数为id,score
    @PutMapping("/violation/{id:\\d+}/{score:\\d+}")
    public Result violation(@PathVariable("id") Integer id,
                            @PathVariable("score") Integer score){
        // 先记录日志开始
        log.info("开始根据学员id和扣分处理违纪：{},{}",id,score);
        // 调用service层的violation方法，根据学员id和扣分处理违纪
        studentService.violation(id,score);
        // 返回成功结果
        return Result.success();
    }

}
