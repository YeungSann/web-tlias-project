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
    // 注入AliyunOSSOperator类
    @Autowired
    private AliyunOSSOperator ossyunOSSOperator;
    /*
    基于本地存储的文件上传

    @PostMapping("/upload")
    // 方法形参中声明前端from表单中的变量，文件必须使用multipartFile类型
    public Result upload(@RequestParam("name") String name,
                         @RequestParam("age") Integer age,
                         @RequestParam("image") MultipartFile image) throws IOException {
        // 测试一下能否接收到参数
        log.info("name: {}, age: {}, image: {}", name, age, image);

        // 调用MultipartFile的getOriginalFilename方法，获取原始文件名
        String originalFileName = image.getOriginalFilename();
        // 随机生成uuid，并去掉中间的-
        String randomStr = UUID.randomUUID().toString().replaceAll("-", "");
        // 获取原始文件扩展名
        String extension = originalFileName.substring((originalFileName.lastIndexOf(".")));
        // 拼接uuid和后缀名，作为新的文件名
        String newFileName = randomStr + extension;

        // 保存文件
        // 调用MultipartFile的transferTo方法，将文件保存到指定路径
        image.transferTo(new File("D:/images/" + newFileName));

        //返回结果
        return Result.success();
    }
     */

    // 基于阿里云OSS的文件上传
    // 调用AliyunOSSOperator类的upload方法
    @PostMapping("/upload")
    public Result upload(@RequestParam("image") MultipartFile image) throws Exception {
        log.info("文件上传:{}", image);

        // 调用AliyunOSSOperator类的upload方法，上传文件到OSS
        String url = ossyunOSSOperator.upload(image);

        log.info("文件上传成功url:{}", url);
        // 返回结果
        return Result.success(url);
    }

}
