package com.javaweb.departmentmanage.service.impl;

import com.javaweb.departmentmanage.mapper.ClazzMapper;
import com.javaweb.departmentmanage.pojo.Clazz;
import com.javaweb.departmentmanage.pojo.ClazzQueryParam;
import com.javaweb.departmentmanage.pojo.PageResult;
import com.javaweb.departmentmanage.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public PageResult<Clazz> listPage(ClazzQueryParam queryParam) {
        // 这里不使用pagehelper插件，直接调用mapper层的findAll方法，获取总记录数
        long total = clazzMapper.findAll(queryParam);
        // 起始索引 = (page - 1) * pageSize 计算出起始索引
        Integer start = (queryParam.getPage() - 1) * queryParam.getPageSize();
        // 计算总行数
        List<Clazz> rows = clazzMapper.findPage(queryParam, start, queryParam.getPageSize());

        // 3. 获取当前日期，动态计算班级状态 status
        LocalDate now = LocalDate.now();
        if (!CollectionUtils.isEmpty(rows)) {
            rows.forEach(clazz -> {
                LocalDate beginDate = clazz.getBeginDate();
                LocalDate endDate = clazz.getEndDate();

                // 规则校验
                if (beginDate != null && now.isBefore(beginDate)) {
                    clazz.setStatus("未开班");
                } else if (endDate != null && now.isAfter(endDate)) {
                    clazz.setStatus("已结课");
                } else {
                    clazz.setStatus("在读中");
                }
            });
        }
        // 把total和rows封装为pageResult对象，返回
        return new PageResult<>(total, rows);
    }

    // 定义一个根据id删除班级的方法，直接调用mapper层的deleteById方法删除
    @Override
    public void deleteById(Integer id) {
        clazzMapper.deleteById(id);
    }

    // 定义一个添加班级的方法，直接调用mapper层的addClazz方法添加
    @Override
    public void addClazz(Clazz clazz) {
        // 调用mapper层的addClazz方法添加班级
        // 先设置创建时间和更新时间为now
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.addClazz(clazz);
    }

    // 定义一个根据id查询班级的方法，直接调用mapper层的findById方法查询
    @Override
    public Clazz findById(Integer id) {
        return clazzMapper.findById(id);
    }

    @Override
    public void updateClazz(Clazz clazz) {
        // 先设置updateTime为now
        clazz.setUpdateTime(LocalDateTime.now());
        // 调用mapper层的updateClazz方法更新班级
        clazzMapper.updateClazz(clazz);
    }

    // 定义一个查询所有班级的方法，直接调用mapper层的findAll方法查询
    @Override
    public List<Clazz> list() {
        // 调用mapper层的findAll方法查询所有班级，返回为clazz的list
        return clazzMapper.getClazzs();
    }
}
