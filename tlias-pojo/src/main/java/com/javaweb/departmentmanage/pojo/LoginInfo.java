package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// 封装登录结果的实体类
public class LoginInfo {
    private Integer id;
    private String username;
    private String name;
    private String token;
}
