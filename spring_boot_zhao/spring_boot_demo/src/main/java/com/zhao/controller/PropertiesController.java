package com.zhao.controller;

import com.zhao.properties.TestProperties;
import com.zhao.properties.ZhaoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertiesController {

    @Autowired
    ZhaoProperties zhaoProperties;

    @Autowired
    TestProperties testProperties;

    @Value("${a}")
    String a;

    @Value("#{1+2}")
    int num;

    @Value("#{${b}+${c}}")
    int num1;


    @Value("${flag}")
    boolean flag;

    @Value("#{${flag} ? 100 : 200}")
    int flagNum;

    @Value("#{T(java.lang.Math).random()}")
    double dd;


    @GetMapping("/test")
    public String test() {
        System.out.println(testProperties.getName());
        System.out.println(a);
        System.out.println(num);
        System.out.println(num1);
        System.out.println(flag);
        System.out.println(flagNum);
        System.out.println(dd);
        System.out.println(zhaoProperties.getEmail());
        System.out.println(zhaoProperties.getChild());
        System.out.println(zhaoProperties.getWife());
        System.out.println(zhaoProperties.getFamily());
        System.out.println(zhaoProperties.getFamily1());
        System.out.println(zhaoProperties.getPay().getAli().getNotifyUrl());
        System.out.println(zhaoProperties.getPay().getWx().getNotifyUrl());
        return "";
    }
}
