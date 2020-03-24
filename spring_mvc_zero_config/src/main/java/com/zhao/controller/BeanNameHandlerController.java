package com.zhao.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-23 20:09
 */
@Component("/user1.do")
public class BeanNameHandlerController implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("BeanNameHandlerController");
    }
}
