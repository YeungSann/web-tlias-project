package com.javaweb.departmentmanage.service.impl;

import com.javaweb.departmentmanage.exception.BusinessException;
import com.javaweb.departmentmanage.mapper.DeptMapper;
import com.javaweb.departmentmanage.mapper.EmpMapper;
import com.javaweb.departmentmanage.pojo.Dept;
import com.javaweb.departmentmanage.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// Service層の実装クラスを作成
// Spring管理下のBean（@Service）として登録
@Service
public class DeptServiceImpl implements DeptService {
    // インジェクション：Mapperインターフェースの実装クラスオブジェクト
    @Autowired
    private DeptMapper deptMapper;
    // インジェクション：社員（Emp）のMapper層オブジェクト
    @Autowired
    private EmpMapper empMapper;

    // 親インターフェースの findAll メソッドを実装し、全部署データを取得
    @Override
    public List<Dept> findAll() {
        // 最下層の Mapper インターフェースのメソッドを呼び出して全データ取得
        return deptMapper.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        // 削除ロジック実行前に、該当部署に所属社員が存在するか判定
        // 社員が存在する場合は削除を許可せず、フロントエンドへエラーメッセージ（BusinessException）を返す
        if (empMapper.findByDeptId(id) != 0L && deptMapper.findById(id) != null) {
            throw new BusinessException("对不起，当前部门下有员工，不能直接删除！");
        }

        // 最下層 Mapper インターフェースの削除メソッドを呼び出す
        deptMapper.deleteById(id);
    }

    @Override
    public void addDept(Dept dept) {
        // 最下層 Mapper インターフェースの追加メソッドを呼び出し、部署を新規登録
        // 部署追加時、作成日時と更新日時を設定する必要がある
        // 現在日時を直接設定することで、フロントエンドからは部署名のみ入力すれば自動で時間が補完される仕様とする
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        // 最下層 Mapper インターフェースの追加メソッドを呼び出す
        deptMapper.addDept(dept);
    }

    @Override
    public Dept findById(Integer id) {
        // 最下層 Mapper インターフェースの照会メソッドを呼び出し、IDに基づき部署データを取得
        return deptMapper.findById(id);
    }

    @Override
    public void updateDept(Dept dept) {
        // 部署更新時、自動的に更新日時を現在日時に設定
        dept.setUpdateTime(LocalDateTime.now());
        // 最下層 Mapper インターフェースの更新メソッドを呼び出し、部署データを更新
        deptMapper.updateDept(dept);
    }
}
