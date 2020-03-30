package com.zhao.runListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-30 22:41
 */
public class ZhaoRunListener2 implements SpringApplicationRunListener {

    public ZhaoRunListener2(SpringApplication application, String[] args) {
    }

    @Override
    public void starting() {
        System.out.println("程序开始准备了2");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {


    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {


    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }
}
