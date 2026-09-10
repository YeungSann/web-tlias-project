package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClazzQueryParam {
    //属性：page、pageSize、name、begin、end
    private Integer page=1;//当前页码
    private Integer pageSize=5;//每页显示条数
    private String name;//班级名称
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin; // 进入时间--范围开始
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end; // 退出时间--范围结束
}
