package com.javaweb.departmentmanage.service;

import com.javaweb.departmentmanage.pojo.ClazzCountOption;
import com.javaweb.departmentmanage.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    JobOption empJobData();

    List<Map<String,Object>> empGenderData();

    List<Map<String, Object>> studentDegreeData();

    ClazzCountOption studentCountData();
}
