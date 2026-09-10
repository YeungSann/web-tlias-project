package com.javaweb.departmentmanage.config;

import com.google.gson.Gson;
import com.javaweb.departmentmanage.utils.AliyunOSSOperator;
import com.javaweb.departmentmanage.utils.AliyunOSSproperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 先写入配置类注解
@Configuration
public class CommonConfig {
    // 这是一个配置类，用来定义第三方bean对象
    @Bean
    public AliyunOSSOperator aliyunOSSOperator(AliyunOSSproperties aliyunOSSproperties){
        // 这里一定要先在AliyunOSSproperties类上添加@Component注解
        // spring会自动在参数中添加@Autowired注解，实现自动注入
        return new AliyunOSSOperator(aliyunOSSproperties);
    }

    // 手动引入gson对象
    @Bean
    public Gson gson(){
        return new Gson();
    }
}
