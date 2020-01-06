package com.example.demo_activity.test1.multidatasource.aop;

import com.example.demo_activity.test1.multidatasource.DSEnum;
import com.example.demo_activity.test1.multidatasource.DataSourceContextHolder;
import com.example.demo_activity.test1.multidatasource.annoation.DataSource;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: yongl
 * @DATE: 2020/1/2 17:52
 */

@Aspect
@Component
@ConditionalOnProperty(prefix = "guns",name = "muti-datasource-open", havingValue = "true")
public class MultiSourceExAop implements Ordered {
    private Logger log = Logger.getLogger(this.getClass());
    @Pointcut(value = "@annotation(com.example.demo_activity.test1.multidatasource.annoation.DataSource)")
    private void cut() {

    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Signature signature = point.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;

        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        DataSource datasource = currentMethod.getAnnotation(DataSource.class);
        if (datasource != null) {
            DataSourceContextHolder.setDataSourceType(datasource.name());
            log.debug("设置数据源为：" + datasource.name());
        } else {
            DataSourceContextHolder.setDataSourceType(DSEnum.DATA_SOURCE_CORE);
            log.debug("设置数据源为：dataSourceCore");
        }

        try {
            return point.proceed();
        } finally {
            log.debug("清空数据源信息：" + (datasource != null ? datasource.name() : ""));
            DataSourceContextHolder.clearDataSourceType();
            //清空之后用默认数据源
            //DataSourceContextHolder.setDataSourceType(DSEnum.DATA_SOURCE_CORE);
            //log.debug("设置数据源为：dataSourceCore");
        }
    }
    /**
     * aop的顺序要早于spring的事务
     */
    @Override
    public int getOrder() {
        return 1;
    }


}
