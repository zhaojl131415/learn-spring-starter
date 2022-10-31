package com.spring.cloud.alibaba.sentinel.controller;

import com.spring.cloud.alibaba.sentinel.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/9/22
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/hi")
    public String sayHi() {
        return "Hello ";
    }

    @GetMapping("/user/info")
    public String getUserInfo() {
        return helloService.getUserInfo();
    }

    @GetMapping("/user/info1")
    public String getUserInfo1() {
        return helloService.getUserInfo();
    }
}
