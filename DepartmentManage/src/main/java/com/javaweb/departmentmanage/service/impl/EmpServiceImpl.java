package com.javaweb.departmentmanage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaweb.departmentmanage.exception.BusinessException;
import com.javaweb.departmentmanage.mapper.EmpExprMapper;
import com.javaweb.departmentmanage.mapper.EmpMapper;
import com.javaweb.departmentmanage.pojo.*;
import com.javaweb.departmentmanage.service.EmpService;
import com.javaweb.departmentmanage.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// emp的service实现类，用来实现empService接口中的方法
@Slf4j
@Service
public class EmpServiceImpl implements EmpService {
    // 注入empMapper对象，用来操作emp表
    @Autowired
    private EmpMapper empMapper;

    // 注入员工经历的mapper对象
    @Autowired
    private EmpExprMapper empExprMapper;

    /*
    // 分页查询方法
    @Override
    public PageResult<Emp> getListPage(Integer page, Integer pageSize) {

        // 调用empMapper对象的getTotal方法查询总记录数
        long total = empMapper.getTotal();

        // 调用empMapper对象的findPage方法查询结果列表
        // 调用之前需要根据 起始索引 = (page - 1) * pageSize 计算出起始索引
        Integer start = (page - 1) * pageSize;
        List<Emp> rows = empMapper.findPage(start, pageSize);

        // 把total和 list封装为pageResult对象，返回
        PageResult<Emp> pageResult = new PageResult<>(total, rows);
        return pageResult;


        // 设置分页参数
        // 这里传的是当前页码和每页记录数两个参数
        PageHelper.startPage(page, pageSize);

        // 调用mapper接口方法
        List<Emp> rows = empMapper.list();
        // 注意：pagehelper仅能对第一个查询方法进行分页，后续的查询方法不会进行分页
        // 也就是即使再调用empMapper对象的findPage方法，也不会进行分页

        // 把rows列表强制转换成page对象，page就直接是列表对象了
        Page<Emp> pages = (Page<Emp>) rows;

        // 解析结果并封装结果返回
        // 最终还是要想controller层返回出总记录数和当前页数据列表
        return new PageResult<>(pages.getTotal(), pages.getResult());
    }

    // 条件分页查询方法
    // 调用mapper中的list2方法
    @Override
    public PageResult<Emp> listPage2(Integer page, Integer pageSize,
                                     String name, Integer gender,
                                     LocalDate begin, LocalDate end) {
        // 设置分页参数
        // 这里传的是当前页码和每页记录数两个参数
        PageHelper.startPage(page, pageSize);

        // 调用mapper接口方法
        // 并且，继续把新增的四个参数往下传递
        List<Emp> rows = empMapper.list2(name, gender, begin, end);
        // 注意：pagehelper仅能对第一个查询方法进行分页，后续的查询方法不会进行分页
        // 也就是即使再调用empMapper对象的findPage方法，也不会进行分页

        // 把rows列表强制转换成page对象，page就直接是列表对象了
        Page<Emp> pages = (Page<Emp>) rows;

        // 解析结果并封装结果返回
        // 最终还是要想controller层返回出总记录数和当前页数据列表
        return new PageResult<>(pages.getTotal(), pages.getResult());
    }
    */

    @Override
    public PageResult<Emp> listPage3(EmpQueryParam empQueryParam) {
        // 设置分页参数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

        // 调用mapper接口方法
        List<Emp> rows = empMapper.list3(empQueryParam);
        Page<Emp> pages = (Page<Emp>) rows;

        // 解析结果并封装结果返回
        return new PageResult<>(pages.getTotal(), pages.getResult());
    }

    // 在方法之上开启事务，保证这里面的两个调用，要么同时成功，要么同时失败
    // 增加rollbackFor属性，指定指定异常类，回滚事务
    @Transactional(rollbackFor = Exception.class) // 自动完成开启事务，提交事务，回滚事务
    @Override
    public void save(Emp emp)  {
        // 调用empMapper对象的insert方法保存员工基本信息
        // 调用之前，先把更新时间设置为当前时间
        emp.setUpdateTime(LocalDateTime.now());
        // 调用之前，先把创建时间设置为当前时间
        emp.setCreateTime(LocalDateTime.now());
        empMapper.saveEmp(emp);

        // 手动抛出一个异常
        //if (true){
            //throw new Exception("出错啦~ ~");
        //}

        // 先获取员工的工作经历列表
        List<EmpExpr> exprList = emp.getExprList();
        // 工作经历的可能分析，可能完全没有工作经历，也可能有工作经历
        // 所以，这里需要判断一下，是否有工作经历

        if (!CollectionUtils.isEmpty(exprList)) {
            // 有工作经历
            // 遍历集合，获取emp的id，同时直接赋值给empExpr对象的empId属性
            exprList.forEach(item -> {
                item.setEmpId(emp.getId());
            });

            // 调用empExprMapper对象的insertBatch方法批量保存员工的工作经历信息
            empExprMapper.saveBatchExpr(exprList);
        }
    }

    // service中调用了两个mapper接口，因此必须保证这两个调用，要么同时成功，要么同时失败
    @Transactional(rollbackFor = Exception.class) // 自动完成开启事务，提交事务，回滚事务
    @Override
    public void deleteById(List<Integer> ids) {
        // 调用empMapper对象的delete方法删除员工基本信息
        empMapper.deleteByIds(ids);
        // 调用empExprMapper对象的deleteByEmpIds方法删除员工表达式信息
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getById(Integer id) {
        // 直接调用empMapper接口，通过外连接查询把员工信息和工作经历信息查询出来
        return empMapper.selectById(id);
    }

    // 添加事务，保证方法中的两个调用，要么同时成功，要么同时失败
    @Transactional(rollbackFor = Exception.class) // 自动完成开启事务，提交事务，回滚事务
    @Override
    public void updateById(Emp emp) {
        // 先调用empMapper对象的updateById方法修改员工基本信息
        // 先更新时间设置为当前时间
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        // 调用empExprMapper对象的deleteByEmpId方法删除员工表达式信息
        // 直接复用上面的批量删除方法，只不过需要我们把emp.getId()转换成集合
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        // 再添加这个员工的工作经历信息
        List<EmpExpr> exprList = emp.getExprList();
        // 判断是否有工作经历，如果有，才需要添加
        if (!CollectionUtils.isEmpty(exprList)) {
            // 拿到员工的id
            exprList.forEach(item ->item.setEmpId(emp.getId()));

            // 调用empExprMapper对象的insertBatch方法批量保存员工的工作经历信息
            empExprMapper.saveBatchExpr(exprList);
        }
    }

    @Override
    public List<Emp> listMaster() {
        return empMapper.listMaster();
    }

     @Override
    public LoginInfo login(LoginDTO loginDTO) {
        // 调用底层mapper获取对应的员工信息
        Emp emp = empMapper.selectByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());

        // 业务逻辑校验：如果查询到的用户信息为空，说明用户名或密码错误
        if (emp == null) {
            // 没有查询到，说明用户名不存在
            throw new BusinessException("用户名或密码错误");
        }
        // 非空，把查询到的用户信息封装到loginInfo中：id，username，name，token
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setId(emp.getId());
        loginInfo.setUsername(emp.getUsername());
        loginInfo.setName(emp.getName());
        // 调用jwtutils工具类，生成令牌
         Map<String,Object> claim = new HashMap<>();
         claim.put("id",emp.getId());
         claim.put("username",emp.getUsername());
        loginInfo.setToken(JwtUtils.generateToken(claim));
        // 把loginInfo返回给控制器
        return loginInfo;
    }


}
