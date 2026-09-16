package com.javaweb.departmentmanage.aop;

import com.javaweb.departmentmanage.mapper.LogMapper;
import com.javaweb.departmentmanage.pojo.Logger;
import com.javaweb.departmentmanage.utils.BaseContext;
import com.javaweb.departmentmanage.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LogRecord {
    /*
    要件：追加・削除・更新操作のログを記録する。記録項目：
    操作者、操作日時、実行メソッドのクラス名、実行メソッド名、実行時引数、戻り値、処理時間

    また、ログ情報をデータベース（MySQL）に保存すること。
     */
    @Autowired
    private LogMapper logMapper;

    @Around("execution(* com.javaweb.departmentmanage.controller.*.delete*(..)) ||" +
            "execution(* com.javaweb.departmentmanage.controller.*.update*(..)) ||" +
            "execution(* com.javaweb.departmentmanage.controller.*.add*(..)) ||" +
            "execution(* com.javaweb.departmentmanage.controller.*.save*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 0. ログのIDを記録：まず既存の最大IDを取得し、nullまたは0の場合は手動で1に設定。
        // nullまたは0でない場合は、既存の最大ID + 1 を使用する
        Integer maxId = logMapper.getMaxId();
        Integer id = (maxId != null && maxId > 0) ? maxId + 1 : 1;
        // 1. 操作者を記録--->今後の拡張：トークン（Token）解析により、現在ログイン中のユーザーIDを取得
        // BaseContextクラスのgetメソッドを用いて、スレッドに保存されたJSONデータ（ログインユーザーID）を取得する
        Integer operateEmpId = BaseContext.getCurrentEmpId();

        // 2. 操作日時を記録
        // 日時オブジェクトを標準の yyyy-MM-dd HH:mm:ss 形式として記録するため、現在時刻（開始時刻）を取得
        LocalDateTime startTime = LocalDateTime.now();

        // 3. 実行メソッドのクラス名を記録
        // 改善：完全修飾クラス名ではなく、簡単なクラス名（SimpleName）を取得することで、フロントエンドでの表示を扱いやすくする
        String className = pjp.getTarget().getClass().getSimpleName();
        // 4. 実行メソッド名を記録
        String methodName = pjp.getSignature().getName();
        // 5. メソッド実行時の引数を記録---＞引数配列を文字列形式に変換
        String args = Arrays.toString(pjp.getArgs());
        // 6. 戻り値を記録し、同時にターゲットメソッドを実行---＞戻り値を文字列形式に変換
        Object result = pjp.proceed();
        // 三項演算子を使用し、resultがnullの場合は空文字/null文字列で代用し、それ以外は result.toString() を使用
        String resultStr = (result != null) ? result.toString() : "null";
        // 7. メソッドの処理時間を記録
        LocalDateTime endTime = LocalDateTime.now();
        long costTime = Duration.between(startTime, endTime).toMillis();
        // 上記の各情報を一つのログオブジェクト（Logger）に格納
        Logger logger = new Logger(id, operateEmpId, startTime, className, methodName, args, resultStr, costTime);
        // ログ情報をデータベースに挿入（インサート）
        logMapper.insert(logger);

        // 8. ログ出力---＞この記述だけではコンソールに出力されるのみで、DBには記録されない点に注意
        // 自作のログオブジェクトを渡すことでログ記録の完了を確認・出力する
        log.info("記録ログ成功：{}", logger);

        // ターゲットメソッドの実行結果を呼び出し元（呼び出し側）へ返却
        return result;
    }
}
