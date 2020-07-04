package com.zhao.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-23 20:07
 */
@Component("/com.zhao.seata.user.do")
public class BeanNameController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("BeanNameController");
        return null;
    }
}
