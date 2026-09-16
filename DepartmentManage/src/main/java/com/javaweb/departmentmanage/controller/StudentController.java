package com.javaweb.departmentmanage.controller;

import com.javaweb.departmentmanage.pojo.PageResult;
import com.javaweb.departmentmanage.pojo.Result;
import com.javaweb.departmentmanage.pojo.Student;
import com.javaweb.departmentmanage.pojo.StudentQueryParam;
import com.javaweb.departmentmanage.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    // まず下位層の StudentService を注入（DI）
    @Autowired
    private StudentService studentService;

    // ページング検索メソッドを作成。入力引数は StudentQueryParam、戻り値は PageResult<Student>
    @GetMapping
    public Result page(StudentQueryParam param) {
        // ログ出力
        log.info("page param: {}", param);
        // PageResultオブジェクトにカプセル化
        PageResult<Student> pageResult = studentService.page(param);
        // 成功結果を返却
        return Result.success(pageResult);
    }

    // ID指定による受講生削除メソッドを作成。リクエスト方式はDELETE、パラメータはID
    // idsコレクション（List）を使用した一括削除処理に変更
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable("ids") List<Integer> ids) {
        // まずログ記録を開始
        log.info("開始：受講生情報削除：{}",ids);
        // Service層の deleteById メソッドを呼び出し、受講生情報を削除
        studentService.deleteById(ids);
        // 成功結果を返却
        return Result.success();
    }

    // 受講生情報追加メソッドを作成。POST方式、リクエストパラメータは Student オブジェクト
    @PostMapping
    public Result add(@RequestBody Student student){
        // まずログ記録を開始
        log.info("開始：受講生情報追加：{}",student);
        // Service層の addStudent メソッドを呼び出し、受講生を追加
        studentService.addStudent(student);
        // 成功結果を返却
        return Result.success();
    }

    // ID指定による受講生情報照会。GET方式、リクエストパラメータはID、レスポンスはResultでラップしたStudentオブジェクト
    @GetMapping("/{id:\\d+}")
    public Result get(@PathVariable("id") Integer id){
        // まずログ記録を開始
        log.info("開始：受講生ID指定による受講生情報照会：{}",id);
        // Service層の getById メソッドを呼び出し、受講生IDに基づいて受講生情報を照会
        Student student = studentService.getById(id);
        // 成功結果を返却
        return Result.success(student);
    }

    // 受講生情報更新メソッドを作成。リクエスト方式はPUT、リクエストパラメータは Student オブジェクト
    @PutMapping
    public Result update(@RequestBody Student student){
        // まずログ記録を開始
        log.info("開始：受講生情報更新：{}",student);
        // Service層の updateStudent メソッドを呼び出し、受講生情報を更新
        studentService.updateStudent(student);
        // 成功結果を返却
        return Result.success();
    }

    // 規律違反処理メソッドを作成。リクエストパスは /violation/{id}/{score}
    // リクエスト方式はPUT、パラメータは id および score
    @PutMapping("/violation/{id:\\d+}/{score:\\d+}")
    public Result violation(@PathVariable("id") Integer id,
                            @PathVariable("score") Integer score){
        // まずログ記録を開始
        log.info("開始：受講生IDおよび減点数に基づく規律違反処理：{},{}",id,score);
        // Service層の violation メソッドを呼び出し、受講生IDと減点数に基づいて規律違反処理を実行
        studentService.violation(id,score);
        // 成功結果を返却
        return Result.success();
    }

}
