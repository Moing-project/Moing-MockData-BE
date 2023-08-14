package com.example.finalteammockdata.global.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DatabaseAspect {

    @Around("@annotation(com.example.finalteammockdata.global.aspect.annotation.TransactionalLockAround)")
    public synchronized Object databaseInputAround(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("databaseInputAround");
        long begin = System.currentTimeMillis();
        Object retVal = joinPoint.proceed(); // 메서드 호출 자체를 감쌈
        System.out.println(System.currentTimeMillis() - begin);
        return retVal;
    }

    @Before("@annotation(com.example.finalteammockdata.global.aspect.annotation.TransactionalLock)")
    public synchronized void databaseInput() throws Throwable{
        log.info("databaseInput");
    }
}
