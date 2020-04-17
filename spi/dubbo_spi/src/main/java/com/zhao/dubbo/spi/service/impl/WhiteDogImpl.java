package com.zhao.dubbo.spi.service.impl;


import com.alibaba.dubbo.common.URL;
import com.zhao.dubbo.spi.service.DogService;

public class WhiteDogImpl implements DogService {
    @Override
    public void getColor() {
        System.out.println("这是一条白狗");
    }

    @Override
    public void getColorByURL(URL url) {
        System.out.println("主人喂的这是一条白狗");
    }
}
