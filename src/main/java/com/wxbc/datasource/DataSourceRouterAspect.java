package com.wxbc.datasource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by zhaochen on 2019/1/26.
 */
@Aspect
@Component
@Order(-999)
@Slf4j
public class DataSourceRouterAspect {

    @Pointcut("@annotation(com.wxbc.datasource.DataSourceRouter)")
    public void methodAspect() {
    }

    /**
     * 在进入Service方法之前执行
     *
     * @param point 切面对象
     */
    @Before("methodAspect()")
    public void before(JoinPoint point) {
        DataSourceRouter dataSourceRouter = getDataSourceRouter(point);

        String dataSourceKey = dataSourceRouter.value();

        if (isSlave(dataSourceKey)) {
            // 标记为读库
            DynamicDataSourceHolder.markSlave();
        } else {
            // 标记为写库
            DynamicDataSourceHolder.markMaster();
        }
    }

    /**
     * 判断是否为读库
     *
     * @param dataSourceKey
     * @return
     */
    private Boolean isSlave(String dataSourceKey) {
        return StringUtils.equals(dataSourceKey, DynamicDataSourceHolder.SLAVE);
    }

    private DataSourceRouter getDataSourceRouter(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getAnnotation(DataSourceRouter.class);
    }
}
