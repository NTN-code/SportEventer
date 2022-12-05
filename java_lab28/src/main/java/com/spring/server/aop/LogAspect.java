package com.spring.server.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(LogAnnotation)")
    public void callController() {
    }

    @Before("callController()")
    public void beforeCallMethod(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp + ", args=[" + args + "]");

    }

    @After("callController()")
    public void afterCallMethod(JoinPoint jp) {
        log.info("after " + jp.toString());
    }
}