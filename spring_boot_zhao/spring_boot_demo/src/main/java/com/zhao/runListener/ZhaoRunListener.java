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
public class ZhaoRunListener implements SpringApplicationRunListener {

    public ZhaoRunListener(SpringApplication application, String[] args) {
    }

    @Override
    public void starting() {
        System.out.println("程序开始准备了");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("环境准备好了");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("初始化spring容器之前的准备工作");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("初始化spring容器准备完成");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("spring容器初始化完成" + context);
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("spring容器运行中" + context);
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }
}
