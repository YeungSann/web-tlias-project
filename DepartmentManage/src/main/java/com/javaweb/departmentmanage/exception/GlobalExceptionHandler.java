package com.javaweb.departmentmanage.exception;

import com.javaweb.departmentmanage.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// グローバル例外ハンドラークラス（システム全体の例外を統一してキャッチ・処理する）
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 汎用例外をキャッチするハンドラーメソッド（他の具体例外的ハンドラーに補足されなかった例外を処理）
    @ExceptionHandler
    public Result handleException(Exception e) {
        // エラーログの記録
        log.error("こちらはグローバル例外ハンドラーです。例外をインターセプト（補足）しました：",e);
        return Result.error("申し訳ございません。サーバー異常が発生しました。時間を置いて再度お試しください。");
    }

    // より詳細な例外キャッチ----＞具体的な例外クラス（重複キー例外）を捕捉
    // 例外サブクラス優先の原則に基づき、まず具体的な例外クラスをキャッチし、該当しない場合に親クラス例外（Exception）へフォールバックする
    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        // エラーログの記録
        log.error("プログラムエラーが発生しました：",e);
        // 具体的なエラーメッセージ文字列を抽出し、フロントエンドへレスポンスとして返却する
        String msg = e.getMessage();
        int i = e.getMessage().indexOf("Duplicate entry");
        String errMsg = msg.substring(i);
        String phone = errMsg.split(" ")[2];
        return Result.error("申し訳ございません。"+phone+" は既に存在します。確認の上、再度お試しください。");
    }

    // 業務例外（BusinessException）クラスをキャッチするハンドラー
    @ExceptionHandler
    public Result handleBusinessException(BusinessException e) {
        // エラーログの記録
        log.error("こちらは業務例外ハンドラーです。ビジネスロジック例外をインターセプト（捕捉）しました：",e);
        return Result.error(e.getMessage());
    }
}