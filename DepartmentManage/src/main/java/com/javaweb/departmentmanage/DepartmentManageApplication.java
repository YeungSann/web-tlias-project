package com.javaweb.departmentmanage;

import com.javaweb.departmentmanage.utils.AliyunOSSOperator;
import com.javaweb.departmentmanage.utils.AliyunOSSproperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;
import org.springframework.context.annotation.Bean;

// Servletスキャナーのアノテーションを追加（Filter, Servlet等のコンポーネントをスキャン）
@ServletComponentScan
@SpringBootApplication
// @MapperアノテーションのみでMapperインターフェースがスキャンできない場合、@MapperScanでスキャン対象パッケージを指定する必要がある
@MapperScan("com.javaweb.departmentmanage.mapper")
public class DepartmentManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DepartmentManageApplication.class, args);
    }



}
