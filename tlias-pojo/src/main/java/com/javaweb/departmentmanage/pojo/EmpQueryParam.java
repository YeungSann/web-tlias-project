package com.javaweb.departmentmanage.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpQueryParam {
    private Integer page=1;//当前页码
    private Integer pageSize=10;//每页显示条数
    private String name;//员工姓名
    private String gender;//性别
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;// 入职时间--范围开始
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;// 入职时间 --- 范围结束
}
