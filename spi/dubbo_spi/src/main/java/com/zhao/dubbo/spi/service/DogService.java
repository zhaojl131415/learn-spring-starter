package com.zhao.dubbo.spi.service;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface DogService {
    void getColor();

    @Adaptive(value = "dogType")
    void getColorByURL(URL url);
}
