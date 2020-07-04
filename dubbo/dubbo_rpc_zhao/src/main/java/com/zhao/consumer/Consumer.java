package com.zhao.consumer;


import com.zhao.framework.ProxyFactory;
import com.zhao.provider.api.HelloService;

/**
 * @author zhao
 */
public class Consumer {

    public static void main(String[] args) throws NoSuchMethodException {

        HelloService proxy = ProxyFactory.getProxy(HelloService.class);
        String result = proxy.sayHello("zzz");
        System.out.println(result);

    }
}
