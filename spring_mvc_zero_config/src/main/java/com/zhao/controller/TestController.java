package com.zhao.controller;

import com.zhao.annotation.ZhaoArg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-22 20:00
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/test.do")
    @ResponseBody                                                   //com.zhao.seata.user 对象  Map
    public Object test(@ZhaoArg Map map, HttpServletRequest request, HttpServletResponse response) {
        //拿当前登录用户的逻辑....
        System.out.println(map.toString());
        // do something
        return map;
    }


    @RequestMapping("/index.do")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }

    @RequestMapping("/hello.do")
    public String hello(HttpServletRequest request, HttpServletResponse response) {
        return "hello";
    }
}
