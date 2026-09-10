package com.javaweb.departmentmanage.controller;

import com.javaweb.departmentmanage.pojo.LoginDTO;
import com.javaweb.departmentmanage.pojo.LoginInfo;
import com.javaweb.departmentmanage.pojo.Result;
import com.javaweb.departmentmanage.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LoginController {
    // 这是一个登录控制类，用来处理登录相关的请求
    // 注入员工服务层，用来调用登录查询方法
    @Autowired
    private EmpService empService;

    // 登录方法，post请求，请求参数为username和password
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        log.info("员工登录: {}", loginDTO.getUsername());
        // 调用底层的登录查询方法,封装在loginInfo中响应出来
        LoginInfo loginInfo = empService.login(loginDTO);
        return Result.success(loginInfo);
    }

}
