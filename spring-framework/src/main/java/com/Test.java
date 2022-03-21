package com;

import com.spring.ZhaoApplicationContext;
import com.zhao.IUserService;
import com.zhao.UserService;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2021-08-09 16:21
 */
public class Test {
    public static void main(String[] args) {
        ZhaoApplicationContext ac = new ZhaoApplicationContext(AppConfig.class);
//        System.out.println(ac.getBean("userService"));
//        System.out.println(ac.getBean("userService"));
//        System.out.println(ac.getBean("userService"));
//        System.out.println(ac.getBean("baseService"));
//        System.out.println(ac.getBean("baseService"));
//        System.out.println(ac.getBean("baseService"));

        IUserService userService = (IUserService) ac.getBean("userService");
        userService.testAop();
    }
}
