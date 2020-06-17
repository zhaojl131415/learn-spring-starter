package com.spring.cloud.alibaba.nacos.consumer.feign.sentinel.controller;

import com.spring.cloud.alibaba.nacos.consumer.feign.sentinel.service.NacosProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-02-09 11:16
 */
@RestController
public class NacosConsumerFeignController {

    @Autowired
    private NacosProviderService nacosProviderService;

    @GetMapping(value = "/hello/hi")
    public String hello() {
        return nacosProviderService.hello("Hi Feign");
    }
}
