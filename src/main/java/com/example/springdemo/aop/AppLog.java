package com.example.springdemo.aop;

import com.example.springdemo.utils.Generator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AppLog {
    @Around("@annotation(auditFilter) && execution(public * *(..))")
    public Object log(ProceedingJoinPoint joinPoint, AuditFilter auditFilter) throws Throwable {
        String requestUUID = new Generator().REQUEST_UUID;
        MDC.put("requestId", requestUUID);  // store UUID in MDC

        Logger log = getLog(joinPoint);
        log.info("==> Request start");

        long startTime = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();

            log.info("==> Execution Time Log: Class: {}, Method: {}, Time Taken: {} ms", className, methodName, (endTime - startTime));

            MDC.remove("requestId"); // cleanup after request
        }
    }

    private Logger getLog(ProceedingJoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getTarget().getClass());
    }
}
