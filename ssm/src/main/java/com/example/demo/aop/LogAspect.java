package com.example.demo.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Objects;

/**
 * @author ccq
 * @since 2021/5/7 14:02
 */
@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final ObjectMapper objectMapper;

    @Around("@annotation(logger)")
    @SneakyThrows
    public Object test(ProceedingJoinPoint joinPoint, Log logger) {
        StopWatch watch = new StopWatch();
        watch.start();
        //执行方法
        Object result = joinPoint.proceed();
        //执行时长(毫秒)
        watch.stop();
        watch.getTotalTimeNanos();

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        //请求的参数
        Object[] args = joinPoint.getArgs();
        String arg = "";
        if (!Objects.isNull(args) && args.length > 0) {
            arg = objectMapper.writeValueAsString(args);
        }
        log.info("{}.{}({}) use {} ns", className, methodName, arg, watch.getLastTaskTimeNanos());
        return result;
    }
}
