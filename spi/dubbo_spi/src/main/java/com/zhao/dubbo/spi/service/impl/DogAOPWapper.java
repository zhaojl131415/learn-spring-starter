package com.zhao.dubbo.spi.service.impl;

import com.alibaba.dubbo.common.URL;
import com.zhao.dubbo.spi.service.DogService;

public class DogAOPWapper implements DogService {

    private DogService dogService;

    public DogAOPWapper(DogService dogService) {
        this.dogService = dogService;
    }

    @Override
    public void getColor() {
        System.out.println("aop before...");
        dogService.getColor();
        System.out.println("aop after...");
    }

    @Override
    public void getColorByURL(URL url) {
        System.out.println("aop before...");
        dogService.getColorByURL(url);
        System.out.println("aop after...");
    }
}
