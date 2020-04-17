package com.zhao.dubbo.spi.service.impl;

import com.alibaba.dubbo.common.URL;
import com.zhao.dubbo.spi.service.DogService;
import com.zhao.dubbo.spi.service.FeedService;

public class PersonFeedDogImpl implements FeedService {

    private DogService dogService;

    public void setDogService(DogService dogService) {
        this.dogService = dogService;
    }

    @Override
    public void feed(URL url) {
        dogService.getColorByURL(url);
    }
}
