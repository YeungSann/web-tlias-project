package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 職歴情報（職歴履歴）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpExpr {
    private Integer id; // ID（主キー）
    private Integer empId; // 社員ID（Emp表との関連キー）
    private LocalDate begin; // 勤務開始日（入社年月）
    private LocalDate end; // 勤務終了日（退職年月）
    private String company; // 勤務先会社名
    private String job; // 役職 / 担当業務
}
