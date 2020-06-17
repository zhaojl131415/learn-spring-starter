package com.zhao.controller;

import org.redisson.Redisson;
import org.redisson.RedissonLock;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
public class ConverterController {

    @Autowired
    private Redisson redisson;

    @GetMapping("/converter")
    public String test(Date date) {
        RLock lock = redisson.getLock("zz");
        lock.lock(30, TimeUnit.SECONDS);
        lock.unlock();

        System.out.println(date);
        return "";
    }
}
