package com.javaweb.departmentmanage.controller;

import com.javaweb.departmentmanage.pojo.*;
import com.javaweb.departmentmanage.service.DeptService;
import com.javaweb.departmentmanage.service.impl.EmpServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

// ログロガー（log）をインポート・自動生成するアノテーション
@Slf4j
// 共通のリクエストパスを /emps に宣言
@RequestMapping("/emps")
// 社員（Emp）関連のリクエストを処理するControllerクラス
@RestController
public class EmpController {
    // 下位層のServiceオブジェクトを注入（DI）。これによりService層のBeanクラスを取得・利用可能にする
    @Autowired
    private EmpServiceImpl empService;
/*
    // GETリクエストを使用し、Serviceの listPage メソッドを呼び出して結果リストを返却する
    @GetMapping
    // @RequestParam アノテーションによりリクエストパラメータを取得。pageのデフォルト値は1、pageSizeのデフォルト値は5
    // pageパラメータが渡されない場合はデフォルト値1、pageSizeが渡されない場合はデフォルト値5が適用される
    public Result listPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        // ログ出力
        log.info("ページング検索：{}，{}", page, pageSize);
        // Serviceの getListPage メソッドを呼び出し、結果リストを返却する
        PageResult<Emp> pageResult = empService.getListPage(page, pageSize);
        return Result.success(pageResult);
    }

    // 条件付きページング検索。Serviceの listPage2 メソッドを呼び出し、結果をレスポンスする
    // 直前のメソッドもGET方式でありパスが衝突するため、別パスを指定するか条件を統一する必要がある
    @GetMapping
    public Result listPage2(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                            // 条件検索で追加された4つのパラメータを追加
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "gender", required = false) Integer gender,
                            // 日付型の begin および end。ユーザーが異なるフォーマットで入力するのを防ぐためフォーマットを統一定義
                            @RequestParam(value = "begin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        // デバッグの便宜を図るため、ログにも条件検索用パラメータ4つを追加出力
        log.info("条件付きページング検索：{}，{}，{}，{}，{},{}", page, pageSize, name, gender, begin, end);
        // Serviceの listPage2 メソッドを呼び出し、結果リストを返却する
        PageResult<Emp> pageResult = empService.listPage2(page, pageSize, name, gender, begin, end);


        return Result.success(pageResult);
    }
 */

    // 条件付きページング検索。Serviceの listPage2 メソッドを呼び出し、結果をレスポンスする
    // 直前のメソッドもGET方式でありパスが同一のため、マッピング衝突に注意する（オブジェクトラップ方式）
    @GetMapping
    public Result listPage3(EmpQueryParam  empQueryParam){
        // デバッグの便宜を図るため、ログにも条件検索パラメータを出力
        log.info("条件付きページング検索：{}", empQueryParam);
        // Serviceの listPage3 メソッドを呼び出し、結果リストを返却する
        PageResult<Emp> pageResult = empService.listPage3(empQueryParam);

        return Result.success(pageResult);
    }

    // POSTリクエストの save メソッドを作成。社員基本情報および職歴情報を保存するために使用
    @PostMapping
    // @RequestBody アノテーションによりリクエストボディ内のJSONデータを取得し、Empオブジェクトに変換
    public Result save(@RequestBody Emp emp)  {
        // ログに社員オブジェクトを追加出力
        log.info("社員情報保存：{}", emp);
        // Serviceの save メソッドを呼び出し、結果を返却
        empService.save(emp);
        return Result.success();
    }

    // DELETEリクエストの社員削除（delete）メソッドを作成。編集画面等の画面描画（表示）確認を経て削除を行う
    @DeleteMapping
    // @RequestParam アノテーションを追加し、リクエストパラメータから削除対象の ids リストを取得
    public Result deleteById(@RequestParam("ids") List<Integer> ids) {
        // まず削除対象の社員ID情報をログ表示
        log.info("社員情報削除：{}", ids);
        // Serviceの deleteById メソッドを呼び出し、該当社員を一括/多件削除
        empService.deleteById(ids);
        return Result.success();
    }


    // 社員情報取得メソッドを作成。GETリクエストを使用し、社員IDを取得してまず該当社員の情報を検索・取得する
    @GetMapping("/{id}")
    // idはパスパラメータ（PathVariable）のため、@PathVariable アノテーションの付与が必須
    public Result getById(@PathVariable("id") Integer id) {
        // まず編集対象の社員情報をログ出力
        log.info("社員情報編集（1件取得）：{}", id);
        // Serviceの getById メソッドを呼び出し、社員情報を検索
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }

    // PUTリクエスト的 updateById メソッドを作成。社員基本情報および職歴情報の更新（修正）に使用
    @PutMapping
    // @RequestBody アノテーションによりリクエストボディ内のJSONデータを取得し、Empオブジェクトに変換
    public Result updateById(@RequestBody Emp emp) {
        // ログに更新対象の社員オブジェクトを追加出力
        log.info("社員情報更新：{}", emp);
        // Serviceの updateById メソッドを呼び出し、結果を返却
        empService.updateById(emp);
        return Result.success();
    }


    // GETリクエストで全社員（担任等）情報を一括取得するメソッドを追加
    @GetMapping("/list")
    public Result listMaster(){
        log.info("全担任情報検索");
        // Serviceの listMaster メソッドを呼び出し、全件リストを返却
        List<Emp> list = empService.listMaster();
        return Result.success(list);
    }

    /**
     * パスワード変更 API
     * @param param ID、新しいパスワード、確認用パスワードを含むパラメータ
     * @return 成功メッセージ（データ本体は含めない）
     */
    @PutMapping("/password")
    public Result updatePassword(@RequestBody EmpPasswordParam param) {
        // サービス層のパスワード更新処理を呼び出し
        empService.updatePassword(param);
        // 正常終了時は成功レスポンスを返却
        return Result.success();
    }

}
