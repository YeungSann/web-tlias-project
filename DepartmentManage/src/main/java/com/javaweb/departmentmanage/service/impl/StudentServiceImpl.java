package com.javaweb.departmentmanage.service.impl;

import com.javaweb.departmentmanage.mapper.StudentMapper;
import com.javaweb.departmentmanage.pojo.PageResult;
import com.javaweb.departmentmanage.pojo.Student;
import com.javaweb.departmentmanage.pojo.StudentQueryParam;
import com.javaweb.departmentmanage.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    // 検索条件付きページング検索メソッド
    @Override
    public PageResult<Student> page(StudentQueryParam param) {
        // まず総レコード数を取得
        long total = studentMapper.getAllLines(param);

        // ページングパラメータ（開始インデックス）を設定
        Integer start = (param.getPage()-1)*param.getPageSize();
        // 検索を実行--->検索結果を List にカプセル化
        List<Student> list = studentMapper.page(param,start,param.getPageSize());

        // total と list を PageResult オブジェクトにカプセル化
        // ページング結果を返却
        return new PageResult<>(total, list);
    }

    @Override
    public void deleteById(List<Integer> ids) {
        // 最下層 Mapper の deleteById メソッドを直接呼び出し、受講生情報を削除
        studentMapper.deleteById(ids);
    }

    // 受講生追加メソッド
    @Override
    public void addStudent(Student student) {
        // 事前に作成日時および更新日時を現在日時に設定
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        // 最下層 Mapper の addStudent メソッドを呼び出し、受講生情報を追加
        studentMapper.addStudent(student);
    }

    // IDによる受講生情報照会メソッド
    @Override
    public Student getById(Integer id) {
        // 最下層 Mapper の getById メソッドを直接呼び出し、受講生IDに基づいて受講生情報を照会
        return studentMapper.getById(id);
    }

    @Override
    public void updateStudent(Student student) {
        // 事前に更新日時を設定
        student.setUpdateTime(LocalDateTime.now());
        // 最下層 Mapper の updateStudent メソッドを呼び出し、受講生情報を更新
        studentMapper.updateStudent(student);
    }

    @Override
    public void violation(Integer id, Integer score) {
        // まず受講生IDに基づいて受講生情報を取得
        Student student = studentMapper.getById(id);

        // score と count は Short 参照型（ラッパークラス）であり、未設定時はデフォルトで null となるため、事前に非空判定（初期化）が必要
        if (student.getViolationCount() == null) {
            student.setViolationCount((short) 0);
        }
        if (student.getViolationScore() == null) {
            student.setViolationScore((short) 0);
        }

        // フロントエンドから減点が入力された後、該当受講生の違反回数を +1 更新
        student.setViolationCount((short) (student.getViolationCount() + 1));
        // 受講生の累積減点スコアを「元のスコア + 入力されたスコア」に更新
        student.setViolationScore((short) (student.getViolationScore() + score));

        // 最後に受講生IDに基づいて受講生情報を更新
        studentMapper.updateStudent(student);
    }
}
