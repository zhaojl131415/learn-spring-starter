package com.spring.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BeanNameListener implements ApplicationListener<InstanceRegisterEvent> {
    @Override
    public void onApplicationEvent(InstanceRegisterEvent e) {
        System.out.println("BeanName监听到了服务注册" + e.getInstanceInfo());
    }


    //spring 怎么知道一共有哪些监听器


    //2个步骤
    //从Bean工厂拿到所有ApplicationListener类型的Bean
    //扫描所有带@EventListener方法对象


    //发布事件?
    //2步
    //判断是否感兴趣
    //调用监听器方法
}
