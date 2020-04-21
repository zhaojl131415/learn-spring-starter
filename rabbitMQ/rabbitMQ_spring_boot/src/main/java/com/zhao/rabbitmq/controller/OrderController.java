package com.zhao.rabbitmq.controller;

import com.zhao.rabbitmq.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("order")
    public void order(String msg){
        orderService.order(msg);
    }
}
