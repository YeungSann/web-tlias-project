package com.javaweb.departmentmanage.exception;

public class BusinessException extends RuntimeException {
    // 業務例外クラス（ビジネスロジック層における例外状況を処理するために使用）
    public BusinessException(String message) {
        super(message);
    }
}
