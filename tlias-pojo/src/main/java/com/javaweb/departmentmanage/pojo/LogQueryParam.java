package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogQueryParam {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String operatorName;
    private String className;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;
}
