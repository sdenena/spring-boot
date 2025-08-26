package com.example.springdemo.aop;

import com.example.springdemo.utils.Generator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AppLog {
    @Around("@annotation(auditFilter) && execution(public * *(..))")
    public Object log(ProceedingJoinPoint joinPoint, AuditFilter auditFilter) throws Throwable {
        var requestUUID = Generator.REQUEST_UUID;
        Logger log = getLog(joinPoint);
        log.info("[{}] ==> Request to controller start ", requestUUID);
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.info("[{}] ==> Execution Time Log: Class: {}, Method: {}, Time Taken: {} ms", requestUUID, className, methodName, (endTime - startTime));

        return result;
    }

    private Logger getLog(ProceedingJoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getTarget().getClass());
    }
}
