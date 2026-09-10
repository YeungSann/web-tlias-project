package com.javaweb.departmentmanage.config;

import com.javaweb.departmentmanage.interceptor.DemoInterceptor;
import com.javaweb.departmentmanage.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 添加配置类标识注解
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /*
    // 更好的处理，直接注入拦截器对象
    @Autowired
    private DemoInterceptor demoInterceptor;
    // 注入token拦截器
    @Autowired
    private TokenInterceptor tokenInterceptor;

    // 重写addInterceptors方法，注册拦截器组件
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 对传入的对象调用添加拦截器方法，指定拦截路径为所有路径
        registry.addInterceptor(tokenInterceptor)
                // 指定拦截路径
                .addPathPatterns("/**")
                // 指定放行路径
                .excludePathPatterns("/login");
    }

     */
}
