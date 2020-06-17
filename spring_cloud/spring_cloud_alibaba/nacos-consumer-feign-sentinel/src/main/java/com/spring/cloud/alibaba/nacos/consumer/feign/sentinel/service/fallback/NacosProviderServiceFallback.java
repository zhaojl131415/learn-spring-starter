package com.spring.cloud.alibaba.nacos.consumer.feign.sentinel.service.fallback;

import com.spring.cloud.alibaba.nacos.consumer.feign.sentinel.service.NacosProviderService;
import org.springframework.stereotype.Component;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-02-09 11:53
 */
@Component
public class NacosProviderServiceFallback implements NacosProviderService {

    @Override
    public String hello(String message) {
        return "hello fallback";
    }
}
