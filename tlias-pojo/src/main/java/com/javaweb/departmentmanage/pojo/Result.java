package com.javaweb.departmentmanage.pojo;

import lombok.Data;

/*
 * 后端同一返回结果
 */
@Data
public class Result {
    private Integer code; // 编码： 1成功，0失败
    private String msg; // 提示信息
    private Object data; // 数据

    // 静态方法：成功返回
    // 无参方法
    public static Result success(){
        Result result = new Result();
        result.code = 1; // 成功编码
        result.msg = "success"; // 成功提示信息
        return result; // 返回成功结果
    }

    // 静态方法：成功返回（重载方法）
    // 有参方法
    public static Result success(Object data){
        Result result = new Result();
        result.data = data; // 成功数据
        result.code = 1; // 成功编码
        result.msg = "success"; // 成功提示信息
        return result; // 返回成功结果
    }

    // 静态方法：失败返回
    public static Result error(String msg){
        Result result = new Result();
        result.code = 0; // 失败编码
        result.msg = msg; // 失败提示信息
        return result; // 返回失败结果
    }
}
