package com.zhao.consumer;


import com.zhao.framework.Proxy;
import com.zhao.provider.api.HelloService;

/**
 * @author tanghf
 * @className consumer.Consumer.java
 * @createTime 2019/8/21 13:27
 */
public class Consumer {

    public static void main(String[] args) throws NoSuchMethodException {

        HelloService proxy = Proxy.getProxy(HelloService.class);
        String result = proxy.sayHello("zzz");
        System.out.println(result);

    }
}
