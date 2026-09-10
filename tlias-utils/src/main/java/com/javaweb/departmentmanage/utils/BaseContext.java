package com.javaweb.departmentmanage.utils;

public class BaseContext {
    // 先创建线程对象
    private static final ThreadLocal<Integer> currentEmpId = new ThreadLocal<>();

    // 保存当前线程的员工id
    public static void setCurrentEmpId(Integer id) {
        currentEmpId.set(id);
    }

    // 获取当前线程的员工id
    public static Integer getCurrentEmpId() {
        return currentEmpId.get();
    }

    // 清除当前线程的员工id---》释放资源
    public static void clearCurrentEmpId() {
        currentEmpId.remove();
    }
}
