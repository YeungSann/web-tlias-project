package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentQueryParam {
    private Integer page=1;// 現在のページ番号（デフォルト値：1）
    private Integer pageSize=10;// 1ページあたりの表示件数（デフォルト値：10）
    private String name;// 氏名（あいまい検索用キーワード）
    private String degree;// 最終学歴
    private Integer clazzId;// 所属クラスID

}
