package com.javaweb.departmentmanage;

import com.google.gson.Gson;
import com.javaweb.departmentmanage.pojo.Result;
import com.javaweb.departmentmanage.utils.AliyunOSSOperator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest
class DepartmentManageApplicationTests {
    @Autowired
    private AliyunOSSOperator AliyunOSSOperator;
    @Autowired
    private Gson gson;



    @Test
    public void testUpload() throws Exception {
        System.out.println("=== 注入的Bean对象为: " + AliyunOSSOperator);

        File file = new File("C:\\Users\\86131\\Pictures\\Screenshots\\002.png");
        //
        FileInputStream fis = new FileInputStream(file);
        //把输入流转为upload方法中的MultipartFile对象
        MultipartFile multipartFile = new MockMultipartFile(
                "image",
                file.getName(),
                "image/png",
                fis
        );

        // 调用upload方法
        String upload = AliyunOSSOperator.upload(multipartFile);
        // 打印上传结果
        System.out.println(upload);


    }

    @Test
    public void testGson()  {
        System.out.println(gson.toJson(Result.success("hello gson")));
    }

}
