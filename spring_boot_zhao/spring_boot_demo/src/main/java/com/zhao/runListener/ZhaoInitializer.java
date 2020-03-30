package com.zhao.runListener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-30 22:43
 */
public class ZhaoInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("回调了initialize方法");
    }
}
