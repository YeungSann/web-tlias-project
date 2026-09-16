package com.javaweb.departmentmanage.controller;

import com.javaweb.departmentmanage.pojo.Clazz;
import com.javaweb.departmentmanage.pojo.ClazzQueryParam;
import com.javaweb.departmentmanage.pojo.PageResult;
import com.javaweb.departmentmanage.pojo.Result;
import com.javaweb.departmentmanage.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    // クラス全件取得（検索）メソッドを定義。GETリクエストに対応し、ClazzのListを返却する
    // 要件：検索条件付きのページング検索（絞り込み検索）
    @GetMapping
    public Result listPage(ClazzQueryParam queryParam){
        // まずログ記録を開始
        log.info("開始：クラス情報検索 :{}",queryParam);
        // Service層のメソッドを呼び出してクラス情報を検索し、結果をPageResultオブジェクトとして取得
        PageResult<Clazz> pageResult = clazzService.listPage(queryParam);

        return Result.success(pageResult);
    }

    // ID指定によるクラス削除メソッドを定義。リクエスト方式はDELETE、リクエストパラメータはID
    // :\d+ はIDが半角数字のみであることを指定（正規表現）。下方の /list パスとのマッピング衝突を回避する
    @DeleteMapping("/{id:\\d+}")
    public Result delete(@PathVariable("id") Integer id){
        // まずログ記録を開始
        log.info("開始：クラス情報削除 :{}",id);
        clazzService.deleteById(id);
        return Result.success();
    }

    // クラス追加メソッドを定義。リクエスト方式はPOST、リクエストパラメータはClazzオブジェクト
    @PostMapping
    public Result addClazz(@RequestBody Clazz clazz){
        // まずログ記録を開始
        log.info("開始：クラス情報追加 :{}",clazz);
        clazzService.addClazz(clazz);
        return Result.success();
    }

    // ID指定によるクラス1件取得メソッドを定義。リクエスト方式はGET、リクエストパラメータはID
    @GetMapping("/{id:\\d+}")
    public Result findById(@PathVariable("id") Integer id){
        // まずログ記録を開始
        log.info("開始：ID指定によるクラス情報検索 :{}",id);
        // IDに該当するClazzオブジェクトを取得
        Clazz clazz = clazzService.findById(id);
        return Result.success(clazz);
    }

    // クラス更新（編集）メソッドを定義。リクエスト方式はPUT、リクエストパラメータはClazzオブジェクト
    @PutMapping
    public Result updateClazz(@RequestBody Clazz clazz){
        // まずログ記録を開始
        log.info("開始：クラス情報更新 :{}",clazz);
        clazzService.updateClazz(clazz);
        return Result.success();
    }

    // /list パスのGETリクエストを定義。ClazzのListを返却する（全件リスト取得用）
    @GetMapping("/list")
    public Result list(){
        // まずログ記録を開始
        log.info("開始：クラス一覧検索");
        // Service層のメソッドを呼び出してクラス情報を取得し、ClazzのListとして受領
        List<Clazz> clazzList = clazzService.list();
        return Result.success(clazzList);
    }
}
