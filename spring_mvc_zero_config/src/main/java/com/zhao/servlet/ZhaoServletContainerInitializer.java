package com.zhao.servlet;

import org.springframework.util.CollectionUtils;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-22 19:49
 */
@HandlesTypes(ZhaoInitializer.class)
public class ZhaoServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("ZhaoServletContainerInitializer onStartup");

        List<ZhaoInitializer> list = new ArrayList<>();

        for (Class<?> clazz : c) {
            try {
                list.add((ZhaoInitializer) clazz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if(!CollectionUtils.isEmpty(list)) {
            list.forEach(initializer -> initializer.start(ctx));
        }
    }
}
