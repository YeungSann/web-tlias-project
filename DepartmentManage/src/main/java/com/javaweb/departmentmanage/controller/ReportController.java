package com.javaweb.departmentmanage.controller;

import com.javaweb.departmentmanage.pojo.ClazzCountOption;
import com.javaweb.departmentmanage.pojo.JobOption;
import com.javaweb.departmentmanage.pojo.Result;
import com.javaweb.departmentmanage.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/empJobData")
    // 无请求参数
    public Result empJobData(){
        log.info("统计员工职位人数");
        // 直接调用service层的empJobData方法
        JobOption jobOption = reportService.empJobData();
        return Result.success(jobOption);
    }

    @GetMapping("/empGenderData")
    // 无请求参数
    public Result empGenderData(){
        log.info("统计员工性别人数");
        // 直接调用service层的empGenderData方法
        List<Map<String,Object>> genderList = reportService.empGenderData();
        return Result.success(genderList);
    }

    @GetMapping("/studentDegreeData")
    public Result studentDegreeData(){
        log.info("统计学员学历人数");
        // 直接调用service层的studentDegreeData方法
        List<Map<String,Object>> degreeList = reportService.studentDegreeData();
        return Result.success(degreeList);
    }

    @GetMapping("/studentCountData")
    public Result studentCountData(){
        log.info("统计班级人数");
        // 直接调用service层的studentCountData方法
        ClazzCountOption clazzCountOption= reportService.studentCountData();
        return Result.success(clazzCountOption);
    }
}
