package com.javaweb.departmentmanage.config;

import com.javaweb.departmentmanage.interceptor.DemoInterceptor;
import com.javaweb.departmentmanage.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 設定クラスの識別アノテーションを付与
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // より良い設計として、インターセプターオブジェクトを直接インジェクションする
    //@Autowired
    //private DemoInterceptor demoInterceptor;
    // トークン検証用インターセプターをインジェクション
    @Autowired
    private TokenInterceptor tokenInterceptor;

    // addInterceptorsメソッドをオーバーライドし、インターセプターコンポーネントを登録する
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 渡されたレジストリオブジェクトに対しインターセプターを追加し、すべてのパス（/**）をインターセプト対象に指定
        registry.addInterceptor(tokenInterceptor)
                // インターセプト（割り込み検証）対象のURLパスを指定
                .addPathPatterns("/**")
                // 除外（除外してアクセスを許可する）URLパスを指定（例：ログイン画面）
                .excludePathPatterns("/login");
    }


}
