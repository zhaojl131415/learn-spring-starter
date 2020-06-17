package com.spring.cloud.alibaba.nacos.consumer.feign.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NacosConsumerFeignSentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerFeignSentinelApplication.class, args);
    }

}
