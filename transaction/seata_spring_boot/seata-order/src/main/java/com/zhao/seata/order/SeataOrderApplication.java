package com.zhao.seata.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 09:55
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SeataOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeataOrderApplication.class, args);
    }
}
