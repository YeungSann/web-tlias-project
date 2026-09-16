package com.javaweb.departmentmanage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Logger {
    // 操作者ID、操作日時、実行メソッドの完全修飾クラス名、実行メソッド名、メソッド実行時パラメータ、戻り値、処理時間
    // operator: {}, operationTime: {}, className: {}, methodName: {}, args: {}, result: {}, costTime: {}
    // 後続処理の便宜を図るため、パラメータ（args）および戻り値（result）も文字列形式（JSON等）に変換して保持する
    private Integer id; // ログID（主キー）
    private Integer operateEmpId; // 操作社員ID
    private LocalDateTime operationTime; // 操作日時
    private String className; // 完全修飾クラス名（実行クラス）
    private String methodName; // メソッド名（操作内容）
    private String args; // リクエスト引数（JSON文字列）
    private String result; // 実行結果 / 戻り値（JSON文字列）
    private Long costTime; // 処理時間（ミリ秒）
}
