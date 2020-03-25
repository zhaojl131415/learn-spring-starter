package com.zhao.controller;

import com.zhao.conditionalOn.A;
import com.zhao.properties.TestProperties;
import com.zhao.properties.ZhaoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConditionalOnController {

    @Autowired
    A a;


    @GetMapping("/conditional")
    public String test() {
        System.out.println(a);
        return "";
    }
}
