package com.zhao.dubbo.spi.service;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface FeedService {
    void feed(URL url);
}
