package com.spring.cloud.alibaba.nacos.consumer.feign.sentinel.service;

import com.spring.cloud.alibaba.nacos.consumer.feign.sentinel.service.fallback.NacosProviderServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-02-09 11:14
 */
@FeignClient(value = "nacos-provider", fallback = NacosProviderServiceFallback.class)
public interface NacosProviderService {

    @GetMapping(value = "/hello/{message}")
    String hello(@PathVariable("message") String message);
}
