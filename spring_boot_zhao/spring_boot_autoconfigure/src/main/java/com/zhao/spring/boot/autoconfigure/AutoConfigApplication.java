package com.zhao.spring.boot.autoconfigure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 参考自: https://www.cnblogs.com/youzhibing/p/10559275.html
 */
@SpringBootApplication
@ImportResource("classpath:spring-bean.xml")
public class AutoConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutoConfigApplication.class, args);
    }
}
