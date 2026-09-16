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
    // 下層の Mapper Bean をインジェクション
    @Autowired
    private LogMapper logMapper;

    @Override
    public PageResult<Logger> logRecord(LogQueryParam queryParam) {
        // ページングの開始インデックスを計算
        Integer start = (queryParam.getPage() - 1) * queryParam.getPageSize();

        // Mapperを呼び出し、現在のページ番号およびページサイズに応じたログリストを取得
        Long total = logMapper.count(queryParam);
        List<Logger> rows = logMapper.page(queryParam, start, queryParam.getPageSize());

        // 2つのパラメータをカプセル化し、ページング結果オブジェクト（PageResult）として返却
        return new PageResult<>(total, rows);
    }
}
