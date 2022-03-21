package com.zhao.core;

import com.spring.BeanPostProccessor;
import com.spring.Component;
import com.zhao.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2021-08-09 17:25
 */
@Component
public class ZhaoBeanPostProccessor implements BeanPostProccessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("初始化前调用BeanPostProccessor");
        if (Objects.equals("userService", beanName)) {
            ((UserService)bean).setName("zhao");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("初始化后调用BeanPostProccessor");

        if (Objects.equals("userService", beanName)) {
            Object proxyInstance = Proxy.newProxyInstance(this.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("aop前置消息");
                    method.invoke(bean, args);
                    System.out.println("aop后置消息");
                    return null;
                }
            });
            return proxyInstance;
        }
        return bean;
    }
}
