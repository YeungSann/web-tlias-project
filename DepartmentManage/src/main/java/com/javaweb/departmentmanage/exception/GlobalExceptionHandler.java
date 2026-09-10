package com.javaweb.departmentmanage.exception;

import com.javaweb.departmentmanage.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 这是一个全局异常处理类
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 这是一个捕获异常的方法
    @ExceptionHandler
    public Result handleException(Exception e) {
        // 记录错误日志
        log.error("这里是全局异常处理器，拦截到异常：",e);
        return Result.error("对不起，服务器异常，请稍后重试");
    }
    
    // 更详细的异常捕获----》捕获具体的异常类
    // 根据异常子类优先原则，先捕获具体的异常类，再捕获父类异常
    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        //记录错误日志
        log.error("程序出错啦：",e);
        // 先截取具体的异常信息，然后返回给前端
        String msg = e.getMessage();
        int i = e.getMessage().indexOf("Duplicate entry");
        String errMsg = msg.substring(i);
        String phone = errMsg.split(" ")[2];
        return Result.error("对不起，"+phone+"已存在，请检查后重试");
    }

    // 捕获业务异常类
    @ExceptionHandler
    public Result handleBusinessException(BusinessException e) {
        // 记录错误日志
        log.error("这里是业务异常处理器，拦截到业务逻辑异常：",e);
        return Result.error(e.getMessage());
    }
}