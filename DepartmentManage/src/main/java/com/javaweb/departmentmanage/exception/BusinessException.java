package com.javaweb.departmentmanage.exception;

public class BusinessException extends RuntimeException {
    // 这是一个业务异常类，用于处理业务层的异常情况
    public BusinessException(String message) {
        super(message);
    }
}
