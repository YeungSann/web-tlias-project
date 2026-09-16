package com.javaweb.departmentmanage.utils;

public class BaseContext {
    // スレッドローカル（ThreadLocal）オブジェクトを生成し、スレッド固有のデータ領域を確保する
    private static final ThreadLocal<Integer> currentEmpId = new ThreadLocal<>();

    // 現在の実行スレッドにログイン中の社員ID（Emp ID）を保存する
    public static void setCurrentEmpId(Integer id) {
        currentEmpId.set(id);
    }

    // 現在の実行スレッドから社員ID（Emp ID）を取得する
    public static Integer getCurrentEmpId() {
        return currentEmpId.get();
    }

    // 現在の実行スレッドに保持されている社員IDを削除（クリア）し、メモリリーク防止のためにリソースを解放する
    public static void clearCurrentEmpId() {
        currentEmpId.remove();
    }
}
