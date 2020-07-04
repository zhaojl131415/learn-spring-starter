package com.zhao.seata.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 09:56
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SeataUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeataUserApplication.class, args);
    }
}
