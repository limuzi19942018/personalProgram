package com.example.demo_activity.test1.multidatasource.aop;

import com.example.demo_activity.test1.multidatasource.DSEnum;
import com.example.demo_activity.test1.multidatasource.DataSourceContextHolder;
import com.example.demo_activity.test1.multidatasource.annoation.DataSource;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: yongl
 * @DATE: 2020/1/6 11:00
 */
@Component
@Aspect
public class TestMethodImpl implements Ordered {
    private Logger log = Logger.getLogger(this.getClass());
    @Pointcut(value = "@annotation(com.example.demo_activity.test1.multidatasource.annoation.TestMethod)")
    private void cut() {

    }
    @Around("cut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        System.out.println("456");

        try {
            return point.proceed();
        } finally {
            System.out.println("123");
        }
    }
    @Before("cut()")
    public void test11(){
        System.out.println("222");
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
