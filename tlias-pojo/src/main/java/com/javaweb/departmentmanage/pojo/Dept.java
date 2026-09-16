package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept {
    private Integer id; // 部署ID（主キー）
    private String name; // 部署名
    private LocalDateTime createTime; // レコード作成日時
    private LocalDateTime updateTime; // レコード更新日時
}
