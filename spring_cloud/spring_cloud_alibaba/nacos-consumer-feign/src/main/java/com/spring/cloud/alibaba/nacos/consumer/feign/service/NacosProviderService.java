package com.spring.cloud.alibaba.nacos.consumer.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-02-09 11:14
 */
@FeignClient(value = "nacos-provider")
public interface NacosProviderService {


    @GetMapping(value = "/hello/{message}")
    String hello(@PathVariable("message") String message);
}
