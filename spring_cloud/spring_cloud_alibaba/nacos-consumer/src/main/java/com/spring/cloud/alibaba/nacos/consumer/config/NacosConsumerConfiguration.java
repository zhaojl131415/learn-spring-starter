package com.spring.cloud.alibaba.nacos.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-02-09 10:58
 */
@Configuration
public class NacosConsumerConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
