package com.zhao.runListener;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-30 22:44
 */
@Component
public class ZhaoRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("程序已经启动了, 这是启动传进来的命令行参数" + args);
    }
}
