package com.javaweb.departmentmanage.service.impl;

import com.javaweb.departmentmanage.mapper.LogMapper;
import com.javaweb.departmentmanage.pojo.LogQueryParam;
import com.javaweb.departmentmanage.pojo.Logger;
import com.javaweb.departmentmanage.pojo.PageResult;
import com.javaweb.departmentmanage.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    // 先注入下层mapper的bean
    @Autowired
    private LogMapper logMapper;

    @Override
    public PageResult<Logger> logRecord(LogQueryParam queryParam) {
        // 计算分页起始索引
        Integer start = (queryParam.getPage() - 1) * queryParam.getPageSize();

        // 调用mapper，查询当前页码和每页大小的日志列表
        Long total = logMapper.count(queryParam);
        List<Logger> rows = logMapper.page(queryParam, start, queryParam.getPageSize());

        // 把两个参数封装，并返回给分页结果对象
        return new PageResult<>(total, rows);
    }
}
