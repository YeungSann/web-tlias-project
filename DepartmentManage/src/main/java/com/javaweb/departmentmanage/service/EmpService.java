package com.javaweb.departmentmanage.service;

import com.javaweb.departmentmanage.pojo.*;

import java.util.List;


public interface EmpService {
    // emp的service层，用来规定service实现类中的方法

    // 获取总记录数和结果列表后，封装为pageResult对象，返回
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
}
