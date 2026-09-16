package com.javaweb.departmentmanage.config;

import com.google.gson.Gson;
import com.javaweb.departmentmanage.utils.AliyunOSSOperator;
import com.javaweb.departmentmanage.utils.AliyunOSSproperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 設定クラスのアノテーションを付与
@Configuration
public class CommonConfig {
    // サードパーティ製（外部ライブラリ等）のBeanを定義・管理するための設定クラス
    @Bean
    public AliyunOSSOperator aliyunOSSOperator(AliyunOSSproperties aliyunOSSproperties){
        // ※前提としてAliyunOSSpropertiesクラスに@Componentが付与されている必要がある
        // Springが自動的に引数に対して@Autowiredと同様の依存性注入（自動インジェクション）を行う
        return new AliyunOSSOperator(aliyunOSSproperties);
    }

    // Gsonオブジェクトを手動でBean登録
    @Bean
    public Gson gson(){
        return new Gson();
    }
}
