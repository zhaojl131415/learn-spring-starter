package com.zhao.provider.api.impl;


import com.zhao.provider.api.HelloService;

/**
 * @author zhao
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String username) {
        return "hello, " + username;
    }
}
