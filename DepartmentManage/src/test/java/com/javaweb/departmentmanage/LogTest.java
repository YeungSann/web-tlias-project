package com.javaweb.departmentmanage;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.time.LocalDateTime;

public class LogTest {
    // 把本单元测试类改造为一个日志测试类

    // 1. 先声明一个常量（日志记录器）
    // 注意，这里是面向接口实现，因此需要使用slf4j接口的类型进行声明
    // 传递的是当前这个类的字节码对象
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LogTest.class);

    @Test
    public void testLog(){
        //System.out.println(LocalDateTime.now() + " : 开始计算...");
        // 改造2. 去掉打印输出，改为使用日志记录
        logger.debug("开始计算...");

        int sum = 0;
        int[] nums = {1, 5, 3, 2, 1, 4, 5, 4, 6, 7, 4, 34, 2, 23};
        for (int num : nums) {
            sum += num;
        }

        // 改造3. 去掉打印输出，改为使用日志记录输出结果
        // 这里的info就是约定了日志级别为info，因此只有info级别及以上的日志，才会被记录到日志文件中
        logger.info("计算结果为: "+sum);
        //System.out.println("计算结果为："+sum);
        //System.out.println(LocalDateTime.now() + "结束计算...");
        // 这里的debug就是约定了日志级别为debug，因此只有debug级别及以上的日志，才会被记录到日志文件中
        logger.debug("结束计算...");

        // 测试一下不同日志级别的输出---》要注意，xml文件中要修改日志级别
        logger.trace("trace日志");
        logger.debug("debug日志");
        logger.info("info日志");
        logger.warn("warn日志");
        logger.error("error日志");
    }

}
