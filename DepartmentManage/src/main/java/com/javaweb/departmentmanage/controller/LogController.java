package com.javaweb.departmentmanage.controller;

import com.javaweb.departmentmanage.pojo.LogQueryParam;
import com.javaweb.departmentmanage.pojo.Logger;
import com.javaweb.departmentmanage.pojo.PageResult;
import com.javaweb.departmentmanage.pojo.Result;
import com.javaweb.departmentmanage.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class LogController {
    // 先注入业务层的bean
    @Autowired
    private LogService logService;

    // 接收请求，请求参数是queryString，包含page和pageSize两个分页参数
    // 限定page和pageSize的默认值1和10，以防用户没有输入
    @GetMapping("/log/page")
    public Result logRecord( LogQueryParam queryParam) {
        // 打印日志
        log.info("分页查询日志记录参数: {}", queryParam);

        // 直接调用业务层的方法，返回pageresult分页结果
        PageResult<Logger> pageResult = logService.logRecord(queryParam);
        // 返回结果
        return Result.success(pageResult);
    }
}
