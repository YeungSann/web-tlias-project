package com.javaweb.departmentmanage;

import com.javaweb.departmentmanage.utils.AliyunOSSOperator;
import com.javaweb.departmentmanage.utils.AliyunOSSproperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;
import org.springframework.context.annotation.Bean;

// 增加servlet扫描器注解
@ServletComponentScan
@SpringBootApplication
// 当只使用mapper注解而无法扫描到mapper接口时，需要使用@Scan注解指定扫描的包
@MapperScan("com.javaweb.departmentmanage.mapper")
public class DepartmentManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DepartmentManageApplication.class, args);
    }



}
