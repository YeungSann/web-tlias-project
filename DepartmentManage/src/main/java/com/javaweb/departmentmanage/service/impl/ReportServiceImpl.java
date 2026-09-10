package com.javaweb.departmentmanage.service.impl;

import com.javaweb.departmentmanage.mapper.EmpMapper;
import com.javaweb.departmentmanage.mapper.StudentMapper;
import com.javaweb.departmentmanage.pojo.ClazzCountOption;
import com.javaweb.departmentmanage.pojo.JobOption;
import com.javaweb.departmentmanage.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    // 注入底层的mapper层
    @Autowired
    private EmpMapper empMapper;

    // 注入学员的mapper层
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JobOption empJobData() {
        // 调用底层的mapper方法，并把list对象释放，分别把key和value装到joboption的jobList、dataList属性中
        JobOption jobOption = new JobOption();
        List<Map<String,Object>> list = empMapper.countJobData();
        // 先对list进行非空判断，非空之后再遍历分别放入到joblist和datalist中
        if(!CollectionUtils.isEmpty(list)){
            list.forEach(map->{
                Object position = map.get("position");
                Object quantity = map.get("quantity");

                // 放入到joblist和datalist中
                jobOption.getJobList().add(position.toString());
                jobOption.getDataList().add(quantity);

            });
        }

        return jobOption;
    }

    @Override
    public List<Map<String,Object>> empGenderData() {
       // 调用底层的mapper方法，并把map对象封装到list中
        return empMapper.countGenderData();
    }

    @Override
    public List<Map<String, Object>> studentDegreeData() {
        // 调用底层的mapper方法，并把map对象封装到list中
        return studentMapper.countDegreeData();
    }

    @Override
    public ClazzCountOption studentCountData() {
        // 调用studentMapper的countCountData方法
        List<Map<String,Object>> list =  studentMapper.countCountData();
        // 新建一个clazzCountOption对象，用于存储班级人数统计结果
        ClazzCountOption clazzCountOption = new ClazzCountOption();

        // 先对list进行非空判断
        if(!CollectionUtils.isEmpty(list)){
            list.forEach(map->{
                Object clazzName = map.get("clazzName");
                Object quantity = map.get("quantity");

                // 放入到clazzlist和datalist中
                clazzCountOption.getClazzList().add(clazzName.toString());
                clazzCountOption.getDataList().add(quantity);

            });
        }
        return clazzCountOption;
    }
}
