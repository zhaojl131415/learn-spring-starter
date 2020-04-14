package com.zhao.server;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("server3")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "test")
    public void test() {
        demoService.test();
    }
}
