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
    // まずビジネスロジック層（Service）のBeanを注入
    @Autowired
    private LogService logService;

    // リクエストを受信。リクエスト参数はQueryString形式で、pageおよびpageSizeの2つのページングパラメータを含む
    // ユーザー未指定時のエラーを防ぐため、pageおよびpageSizeのデフォルト値をそれぞれ1および10に制限・設定
    @GetMapping("/log/page")
    public Result logRecord( LogQueryParam queryParam) {
        // ログを出力
        log.info("操作ログ記録ページング検索パラメータ: {}", queryParam);

        // ビジネスロジック層のメソッドを直接呼び出し、PageResult形式のページング検索結果を取得
        PageResult<Logger> pageResult = logService.logRecord(queryParam);
        // 成功結果を返却
        return Result.success(pageResult);
    }
}
