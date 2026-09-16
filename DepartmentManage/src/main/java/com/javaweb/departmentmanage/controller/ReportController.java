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
    // リクエストパラメータなし
    public Result empJobData(){
        log.info("役職別社員人数の集計");
        // Service層の empJobData メソッドを直接呼び出す
        JobOption jobOption = reportService.empJobData();
        return Result.success(jobOption);
    }

    @GetMapping("/empGenderData")
    // リクエストパラメータなし
    public Result empGenderData(){
        log.info("性別別社員人数の集計");
        // Service層の empGenderData メソッドを直接呼び出す
        List<Map<String,Object>> genderList = reportService.empGenderData();
        return Result.success(genderList);
    }

    @GetMapping("/studentDegreeData")
    public Result studentDegreeData(){
        log.info("最終学歴別受講生人数の集計");
        // Service層の studentDegreeData メソッドを直接呼び出す
        List<Map<String,Object>> degreeList = reportService.studentDegreeData();
        return Result.success(degreeList);
    }

    @GetMapping("/studentCountData")
    public Result studentCountData(){
        log.info("クラス別在籍人数の集計");
        // Service層の studentCountData メソッドを直接呼び出す
        ClazzCountOption clazzCountOption= reportService.studentCountData();
        return Result.success(clazzCountOption);
    }
}
