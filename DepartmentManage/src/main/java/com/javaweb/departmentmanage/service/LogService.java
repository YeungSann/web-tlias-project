package com.javaweb.departmentmanage.service;

import com.javaweb.departmentmanage.pojo.LogQueryParam;
import com.javaweb.departmentmanage.pojo.Logger;
import com.javaweb.departmentmanage.pojo.PageResult;

public interface LogService {

    // 定义一个方法，用来查询日志记录
    PageResult<Logger> logRecord(LogQueryParam queryParam);
}
