package com.zhao.seata.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 13:10
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SeataStockApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeataStockApplication.class, args);
    }
}
