package com.spring;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2021-08-09 17:22
 */
public interface BeanPostProccessor {

    Object postProcessBeforeInitialization(Object bean, String beanName);


    Object postProcessAfterInitialization(Object bean, String beanName);
}
