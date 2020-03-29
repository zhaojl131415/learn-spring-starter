package com.spring.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener   {


    //new 包装类 把 扫描到的方法对象 放到包装类里面


    //你感兴趣的事件  param1:
    //InstanceRegisterEvent我们自 己的注册中心启动事件
    //对任意事件感兴趣 当参数是Object
    @EventListener
    public void test(InstanceRegisterEvent e){

        System.out.println("监听到了服务注册"+e.getInstanceInfo());
    }

}
