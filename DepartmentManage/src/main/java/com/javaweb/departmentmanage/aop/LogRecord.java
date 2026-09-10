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
    需求：记录增、删、改 操作日志，包括：
    操作人、操作时间、执行方法的全类名、执行方法名、方法运行时参数、返回值、方法执行时长

    还要求把日志记录到数据库中
     */
    @Autowired
    private LogMapper logMapper;

    @Around("execution(* com.javaweb.departmentmanage.controller.*.delete*(..)) ||" +
            "execution(* com.javaweb.departmentmanage.controller.*.update*(..)) ||" +
            "execution(* com.javaweb.departmentmanage.controller.*.add*(..)) ||" +
            "execution(* com.javaweb.departmentmanage.controller.*.save*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 0. 记录日志的id， 先获取原有的id值，如果为null或0，则手动设为1，
        // 如果不为null或0，则直接使用原有的id值+1
        Integer maxId = logMapper.getMaxId();
        Integer id = (maxId != null && maxId > 0) ? maxId + 1 : 1;
        // 1. 记录操作人--->后续拓展：通过token解析，获取当前登录用户的id
        // 从BaseContext类中get方法，获取保存的json数据
        Integer operateEmpId = BaseContext.getCurrentEmpId();

        // 2. 记录操作时间
        // 把时间对象转为标准的yyyy-MM-dd HH:mm:ss格式
        LocalDateTime startTime = LocalDateTime.now();

        // 3. 记录执行方法的全类名
        // 改进：不要获取全类名，而是获取具体的类名，这样前端显示的时候更方便
        String className = pjp.getTarget().getClass().getSimpleName();
        // 4. 记录执行方法名
        String methodName = pjp.getSignature().getName();
        // 5. 记录方法运行时参数---》把参数转换为字符串类型
        String args = Arrays.toString(pjp.getArgs());
        // 6. 记录返回值 同时 执行目标方法---》把返回值转换为字符串类型
        Object result = pjp.proceed();
        // 用三目运算符，判断result是否为null，如果是null，就用空字符串代替，否则就用result.toString()方法
        String resultStr = (result != null) ? result.toString() : "null";
        // 7. 记录方法执行时长
        LocalDateTime endTime = LocalDateTime.now();
        long costTime = Duration.between(startTime, endTime).toMillis();
        // 先把以上内容记录到一个日志对象中
        Logger logger = new Logger(id, operateEmpId, startTime, className, methodName, args, resultStr, costTime);
        // 把日志记录到数据库中
        logMapper.insert(logger);

        // 8. 记录日志---》不能这样写，这样日志会直接输出到控制台，而不是记录到数据库中
        // 直接把自定义的日志对象传入即可完成记录
        log.info("记录日志成功：{}", logger);

        // 把目标方法的执行结果返回给调用者
        return result;
    }
}
