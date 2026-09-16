package com.javaweb.departmentmanage.controller;

import com.javaweb.departmentmanage.pojo.Dept;
import com.javaweb.departmentmanage.pojo.Result;
import com.javaweb.departmentmanage.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Slf4jログアノテーションを宣言し、ログロガー（log）を自動追加する
@Slf4j
// 共通のリクエストパスを宣言。以下の各メソッドでパスを重複して記述する必要をなくす
@RequestMapping("/depts")
// 本クラスがHTTPリクエストを処理するコントローラークラス（@Controller + @ResponseBody）であることを宣言
@RestController
public class DeptController {
    // ログ定数オブジェクトを生成し、本クラスのバイトコードオブジェクトを渡す
    // より簡略化された方法として、本クラスの上部に @Slf4j アノテーションを付与することで、直接 log オブジェクトを使用可能になる
    // private static final Logger log = LoggerFactory.getLogger(DeptController.class);

    // Serviceインターフェースを注入（DI）
    @Autowired
    private DeptService deptService;

    // リクエスト処理メソッドを定义。戻り値はリクエストの成功/失敗を確認するためのResultオブジェクト
    // HTTPリクエストメソッドを指定
    //@RequestMapping(value = "/depts", method = RequestMethod.GET)
    // @GetMapping を使用してリクエストメソッド（GET）とパスを直接指定することも可能
    // この書き方はPOSTやDELETEなどの他のHTTPメソッドにも同様に適用できる
    @GetMapping
    public Result list(){
        // ログロガー（log）を使用した出力・記録処理へ変更
        log.info("部署データの全件検索");
        //System.out.println("部署データの全件検索");

        // deptServiceの全件検索メソッドを呼び出し、すべての部署データを取得する
        // 取得した結果をListコレクションに格納する
        List<Dept> deptList = deptService.findAll();

        // 処理がここまで到達すれば検索成功を意味するため、成功結果（Result.success）をそのまま返却する
        return Result.success(deptList);
    }

    // 部署削除用のリクエスト処理メソッドを定義
    // @RequestParam アノテーションを付与すると、idパラメータの送信が必須となる。送信されない場合はエラーが発生する
    // 必須となる理由は、内部の required 属性のデフォルト値が true に設定されているためである
    // 手動で false に変更することも可能

    // なお、@RequestParam で指定する属性名とメソッドの引数名が一致している場合は、@RequestParam の記述を省略可能
    // 実際の業務ロジック：Controller層はフロントエンドから送信されたリクエストパラメータ（部署ID）を受け取るために使用する
    public Result deleteById(@RequestParam(value = "id", required = false) Integer id){
        // ログロガー（log）を使用した出力・記録处理へ変更
        log.info("部署削除:{}",id);// {} はプレースホルダー（占位符）であり、idパラメータの値が入る
        //System.out.println("部署削除:"+id);

        // deptServiceの削除メソッドを呼び出し、該当する部署を削除する
        deptService.deleteById(id);
        // 処理がここまで到達すれば削除成功を意味するため、成功メッセージ付きの成功結果を返却する
        return Result.success("削除成功");
    }

    /*
    非推奨の書き方

    // サーブレット標準に近い従来の方法：HttpServletRequest を使用してリクエストパラメータ id を取得する
    @DeleteMapping("/depts")
    public Result deleteById2(HttpServletRequest request){
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        System.out.println("部署削除"+id);

        // 削除操作では追加のデータ返却が不要なため、Resultの引数なし success メソッドを直接呼び出して返却する
        return Result.success();
    }
     */


    // 部署新規登録（追加）メソッドを記述
    // 重要なポイント：@RequestBody アノテーションを使用して、フロントエンドから送信されたJSON文字列を対応するエンティティオブジェクトに変換し、Controller層の引数に代入する
    @PostMapping
    public Result addDept(@RequestBody Dept dept){
        // ログロガー（log）を使用した出力・記録処理へ変更
        log.info("部署新規登録:{}",dept);
        //System.out.println("部署新規登録:"+dept);

        // deptServiceの登録メソッドを呼び出し、新しい部署を追加する
        deptService.addDept(dept);
        // 処理がここまで到達すれば登録成功を意味するため、成功結果を返却する
        return Result.success("部署追加成功");
    }

    // ID指定による部署データ検索メソッドを記述
    // {} 識別子を使用してパスパラメータ（Path Variable）を表し、idがパスパラメータの名前となる
    @GetMapping("/{id}")
    // パスパラメータ id を受け取るために、必ず @PathVariable アノテーションを使用する
    // なお、@PathVariable アノテーションで名前を明示しない場合でも、パスパラメータ名と引数名が一致していればバインド可能
    public Result findById(@PathVariable("id") Integer id){
        // ログロガー（log）を使用した出力・記録処理へ変更
        log.info("ID指定による部署データ検索:{}",id);// {} はプレースホルダーであり、idパラメータの値が入る
        //System.out.println("ID指定による部署データ検索:"+id);

        // deptServiceの検索メソッドを呼び出し、IDに基づいて部署データを取得する
        Dept dept = deptService.findById(id);
        // 処理がここまで到達すれば検索成功を意味するため、成功結果を返却する
        return Result.success(dept);
    }

    // 部署更新（修改）メソッドを記述
    // @RequestBody アノテーションが付与されており、フロントエンドから送信されたJSON文字列をエンティティオブジェクトに変換して引数に代入する
    public Result updateDept(@RequestBody Dept dept){
        // ログロガー（log）を使用した出力・記録処理へ変更
        log.info("部署データ更新:{}",dept);
        //System.out.println("部署更新:"+dept);

        // 現在の部署情報を取得後、Service層の更新メソッドを呼び出して変更操作を完了させる
        deptService.updateDept(dept);
        // 処理がここまで到達すれば更新成功を意味するため、成功結果を返却する
        return Result.success("部署データ更新成功");
    }
}
