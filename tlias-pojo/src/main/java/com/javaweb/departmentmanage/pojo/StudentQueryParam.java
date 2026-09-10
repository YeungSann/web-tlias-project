package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentQueryParam {
    private Integer page=1;//当前页码
    private Integer pageSize=10;//每页显示条数
    private String name;//姓名
    private String degree;//最高学历
    private Integer clazzId;//所属班级

}
