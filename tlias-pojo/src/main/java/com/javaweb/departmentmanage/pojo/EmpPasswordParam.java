package com.javaweb.departmentmanage.pojo;

import lombok.Data;

@Data
public class EmpPasswordParam {
    private Integer id;
    private String newPassword;
    private String rePassword; // 確認用パスワード
}