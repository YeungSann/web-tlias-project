package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Logger {
    //操作人、操作时间、执行方法的全类名、执行方法名、方法运行时参数、返回值、方法执行时长
    // operator: {}, operationTime: {}, className: {}, methodName: {}, args: {}, result: {}, costTime: {}
    // 为了方便后续的处理，把参数类型args、返回值result也转换成字符串类型
    private Integer id;
    private Integer operateEmpId;
    private LocalDateTime operationTime;
    private String className;
    private String methodName;
    private String args;
    private String result;
    private Long costTime;
}
