package com.javaweb.departmentmanage.pojo;

import lombok.Data;

/*
 * バックエンド統一レスポンス結果クラス（API共通フォーマット）
 */
@Data
public class Result {
    private Integer code; // ステータスコード： 1成功，0失敗
    private String msg; // メッセージ（レスポンス情報）
    private Object data; // レスポンスデータ

    // 静的メソッド：成功時の返却（データなし）
    // 引数なしメソッド
    public static Result success(){
        Result result = new Result();
        result.code = 1; // 成功ステータスコード
        result.msg = "success"; // 成功メッセージ
        return result; // 成功結果を返却
    }

    // 静的メソッド：成功時の返却（オーバーロードメソッド）
    // 引数ありメソッド（データ保持）
    public static Result success(Object data){
        Result result = new Result();
        result.data = data; // レスポンスデータ設定
        result.code = 1; // 成功ステータスコード
        result.msg = "success"; // 成功メッセージ
        return result; // 成功結果を返却
    }

    // 静的メソッド：失敗時の返却
    public static Result error(String msg){
        Result result = new Result();
        result.code = 0; // 失敗ステータスコード
        result.msg = msg; // エラーメッセージ設定
        return result; // 失敗結果を返却
    }
}
