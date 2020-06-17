package com.spring.cloud.alibaba.nacos.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-02-09 00:52
 */
@RestController
public class HelloController {
    @Value("${server.port}")
    private String port;

    // 注入配置文件上下文
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    // 从上下文中读取配置
    @GetMapping(value = "/hi")
    @RequestScope
    public String sayHi() {
        return "Hello " + applicationContext.getEnvironment().getProperty("nacos.user.name");
    }

    @GetMapping(value = "/hello/{message}")
    public String hello(@PathVariable String message) {
//        return "Hello Nacos Discovery " + message;
        return "Hello Nacos Discovery " + message + " i am from port " + port;
    }
}
