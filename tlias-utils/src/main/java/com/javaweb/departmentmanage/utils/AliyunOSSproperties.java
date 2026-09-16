package com.javaweb.departmentmanage.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
// @Component アノテーションを付与し、本クラスをSpring IoCコンテナに登録する
@Component
// 設定ファイル（application.yml等）のプレフィックスを指定し、該当する設定値を自動的にバインド（取得）する
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSproperties {
    // 設定ファイル（application.yml等）のパラメータ（接続情報）を受け取るためのエンティティクラス（プロパティ保持クラス）
    private String endpoint ;
    private String bucketName ;
    private String region ;
}
