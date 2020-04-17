package com.zhao.dubbo.spi;


import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.zhao.dubbo.spi.service.DogService;
import com.zhao.dubbo.spi.service.FeedService;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class DubboSPIDemo {
    public static void main(String[] args) {
        // =============================================1=============================================
        // 每个接口对应一个ExtensionLoader
//        ExtensionLoader<DogService> load = ExtensionLoader.getExtensionLoader(DogService.class);
//        DogService white = load.getExtension("white");
//        white.getColor();
//
//        DogService black = load.getExtension("black");
//        black.getColor();


        // =============================================2=============================================
        ExtensionLoader<FeedService> extensionLoader = ExtensionLoader.getExtensionLoader(FeedService.class);
        FeedService feed = extensionLoader.getExtension("feed");

        Map<String, String> map = new HashMap();
        map.put("dogType", "white");
        URL url = new URL("", "", 0, map);
        feed.feed(url);


    }
}
