package com.zhao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ConverterController {

    @GetMapping("/converter")
    public String test(Date date) {
        System.out.println(date);
        return "";
    }
}
