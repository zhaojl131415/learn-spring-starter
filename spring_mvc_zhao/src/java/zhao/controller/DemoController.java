package com.zhao.controller;

import com.zhao.annotation.ZhaoController;
import com.zhao.annotation.ZhaoRequestMapping;
import com.zhao.annotation.ZhaoResponseBody;
import com.zhao.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-22 14:50
 */
@ZhaoController
@ZhaoRequestMapping("/demo")
public class DemoController {

    @ZhaoRequestMapping("/hello.do")
    public Object hello(){
        return "hello";
    }



    @ZhaoRequestMapping("/test.do")
    @ZhaoResponseBody
    public Object test(String name, HttpServletRequest request, HttpServletResponse response, UserEntity userEntity){
        request.getParameter("name");
        System.out.println(name);
        System.out.println(request);
        System.out.println(response);
        System.out.println(userEntity);
        return  "test";

    }
}
