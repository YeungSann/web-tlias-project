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
        // PageHelperプラグインを使用せず、Mapper層の findAll メソッドを直接呼び出して総レコード数を取得
        long total = clazzMapper.findAll(queryParam);
        // 開始インデックス = (page - 1) * pageSize により開始位置を計算
        Integer start = (queryParam.getPage() - 1) * queryParam.getPageSize();
        // ページングデータ（該当ページの全件リスト）を取得
        List<Clazz> rows = clazzMapper.findPage(queryParam, start, queryParam.getPageSize());

        // 3. 現在の日付を取得し、クラスのステータス（status）を動的に計算
        LocalDate now = LocalDate.now();
        if (!CollectionUtils.isEmpty(rows)) {
            rows.forEach(clazz -> {
                LocalDate beginDate = clazz.getBeginDate();
                LocalDate endDate = clazz.getEndDate();

                // ルール判定
                if (beginDate != null && now.isBefore(beginDate)) {
                    clazz.setStatus("未开班");
                } else if (endDate != null && now.isAfter(endDate)) {
                    clazz.setStatus("已结课");
                } else {
                    clazz.setStatus("在读中");
                }
            });
        }
        // total と rows を PageResult オブジェクトにカプセル化して返却
        return new PageResult<>(total, rows);
    }

    // IDによるクラス削除メソッドを定義。Mapper層の deleteById メソッドを直接呼び出して削除
    @Override
    public void deleteById(Integer id) {
        clazzMapper.deleteById(id);
    }

    // クラス追加メソッドを定義。Mapper層の addClazz メソッドを直接呼び出して追加
    @Override
    public void addClazz(Clazz clazz) {
        // Mapper層の addClazz メソッドを呼び出してクラスを追加
        // 事前に作成日時および更新日時を現在日時に設定
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.addClazz(clazz);
    }

    // IDによるクラス照会メソッドを定義。Mapper層の findById メソッドを直接呼び出して照会
    @Override
    public Clazz findById(Integer id) {
        return clazzMapper.findById(id);
    }

    @Override
    public void updateClazz(Clazz clazz) {
        // 事前に updateTime を現在日時に設定
        clazz.setUpdateTime(LocalDateTime.now());
        // Mapper層の updateClazz メソッドを呼び出してクラス情報を更新
        clazzMapper.updateClazz(clazz);
    }

    // 全クラス取得メソッドを定義。Mapper層の getClazzs メソッドを直接呼び出して照会
    @Override
    public List<Clazz> list() {
        // Mapper層の getClazzs メソッドを呼び出して全クラスを取得し、ClazzのListとして返却
        return clazzMapper.getClazzs();
    }
}
