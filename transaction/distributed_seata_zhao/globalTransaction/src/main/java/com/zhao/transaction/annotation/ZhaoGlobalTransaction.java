package com.zhao.transaction.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 全局事务 注解
 * @author zhaojinliang
 * @date 2020-04-14 15:41
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZhaoGlobalTransaction {
    // 代表属于分布式事务
    // 当前子事务是否为第一个
    boolean isStart() default false;
    // 当前子事务是否为最后一个
    boolean isEnd() default false;
}
