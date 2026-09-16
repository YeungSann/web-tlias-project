package com.javaweb.departmentmanage.controller;

import com.javaweb.departmentmanage.pojo.Result;
import com.javaweb.departmentmanage.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {
    // AliyunOSSOperator クラスを注入（DI）
    @Autowired
    private AliyunOSSOperator ossyunOSSOperator;
    /*
    ローカルストレージに基づくファイルアップロード処理

    @PostMapping("/upload")
    // メソッドの仮引数にフロントエンドForm表单の変数を宣言。ファイルはMultipartFile型を使用する必要がある
    public Result upload(@RequestParam("name") String name,
                         @RequestParam("age") Integer age,
                         @RequestParam("image") MultipartFile image) throws IOException {
        // パラメータが正しく受信できているかテストログを出力
        log.info("name: {}, age: {}, image: {}", name, age, image);

        // MultipartFile の getOriginalFilename メソッドを呼び出し、元のファイル名を取得
        String originalFileName = image.getOriginalFilename();
        // UUIDをランダム生成し、途中のハイフン（-）を削除
        String randomStr = UUID.randomUUID().toString().replaceAll("-", "");
        // 元のファイルの拡張子を取得
        String extension = originalFileName.substring((originalFileName.lastIndexOf(".")));
        // UUIDと拡張子を結合し、新しいファイル名を作成
        String newFileName = randomStr + extension;

        // ファイルを保存
        // MultipartFile の transferTo メソッドを呼び出し、指定したローカルパスにファイルを保存
        image.transferTo(new File("D:/images/" + newFileName));

        // 成功結果を返却
        return Result.success();
    }
     */

    // アリババクラウドOSS（Aliyun OSS）に基づくファイルアップロード
    // AliyunOSSOperator クラスの upload メソッドを呼び出す
    @PostMapping("/upload")
    public Result upload(@RequestParam("image") MultipartFile image) throws Exception {
        log.info("ファイルアップロード:{}", image);

        // AliyunOSSOperator クラスの upload メソッドを呼び出し、ファイルをOSSへアップロード
        String url = ossyunOSSOperator.upload(image);

        log.info("ファイルアップロード成功 URL:{}", url);
        // 成功結果（アクセスURL付き）を返却
        return Result.success(url);
    }

}
