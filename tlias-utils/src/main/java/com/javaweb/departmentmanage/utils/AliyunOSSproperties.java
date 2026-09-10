package com.javaweb.departmentmanage.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
// 添加component注解，将类添加到spring容器中
@Component
// 添加配置文件中的前缀，用来接收前缀下的值
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSproperties {
    // 这是一个实体类，用来接收配置文件中的参数
    private String endpoint ;
    private String bucketName ;
    private String region ;
}
