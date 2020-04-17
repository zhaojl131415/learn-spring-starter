package com.zhao.java.spi.service.impl;

import com.zhao.java.spi.service.DogService;

public class WhiteDogImpl implements DogService {
    @Override
    public void getColor() {
        System.out.println("这是一条白狗");
    }
}
