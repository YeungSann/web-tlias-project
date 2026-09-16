package com.javaweb.departmentmanage.service;

import com.javaweb.departmentmanage.pojo.*;

import java.util.List;


public interface EmpService {
    // emp の Service 層：Service 実装クラスで実装すべきメソッドを定義

    // 総レコード数および結果リストを取得後、PageResult オブジェクトにカプセル化して返却する
    /*
    PageResult<Emp> getListPage(Integer page, Integer pageSize);


    PageResult<Emp> listPage2(Integer page, Integer pageSize,
                                 String name, Integer gender,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end);

     */

    PageResult<Emp> listPage3(EmpQueryParam empQueryParam);

    void save(Emp emp) throws Exception;

    void deleteById(List<Integer> ids);

    Emp getById(Integer id);

    void updateById(Emp emp);

    List<Emp> listMaster();

    LoginInfo login(LoginDTO loginDTO);

    void updatePassword(EmpPasswordParam param);

}
