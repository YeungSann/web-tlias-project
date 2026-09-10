package com.javaweb.departmentmanage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class BeanTest {
    // 先注入ioc容器
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test(){
        for (int i = 0; i < 1000; i++) {
            // 默认的bean名时小驼峰命名法
            Object deptController = applicationContext.getBean("deptController");
            // 打印地址，看是否单例
            System.out.println(deptController);
        }
    }
}
