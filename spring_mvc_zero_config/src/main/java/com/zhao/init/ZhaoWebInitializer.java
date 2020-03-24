package com.zhao.init;

import com.zhao.servlet.ZhaoInitializer;

import javax.servlet.ServletContext;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-22 23:27
 */
public class ZhaoWebInitializer implements ZhaoInitializer {
    @Override
    public void start(ServletContext context) {
        System.out.println("ZhaoWebInitializer start 1");
    }
}
