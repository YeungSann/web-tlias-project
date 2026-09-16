package com.javaweb.departmentmanage.service;

import com.javaweb.departmentmanage.pojo.LogQueryParam;
import com.javaweb.departmentmanage.pojo.Logger;
import com.javaweb.departmentmanage.pojo.PageResult;

public interface LogService {

    // ログ記録を照会・取得するためのメソッドを定義
    PageResult<Logger> logRecord(LogQueryParam queryParam);
}