package com.zhao;

import java.util.LinkedHashMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-14 20:37
 */
public class ServerIps {

    public static final List<String> IPS = Arrays.asList(
            "192.168.100.1",
            "192.168.100.2",
            "192.168.100.3",
            "192.168.100.4",
            "192.168.100.5",
            "192.168.100.6",
            "192.168.100.7",
            "192.168.100.8",
            "192.168.100.9",
            "192.168.100.10"
    );

    public static final Map<String, Integer> WEIGHT_IPS = new LinkedHashMap<>();

    static {
//        WEIGHT_IPS.put("192.168.100.1", 5);
//        WEIGHT_IPS.put("192.168.100.2", 9);
//        WEIGHT_IPS.put("192.168.100.3", 8);
//        WEIGHT_IPS.put("192.168.100.4", 7);
//        WEIGHT_IPS.put("192.168.100.5", 6);
//        WEIGHT_IPS.put("192.168.100.6", 5);
        WEIGHT_IPS.put("192.168.100.7", 5);
        WEIGHT_IPS.put("192.168.100.8", 1);
        WEIGHT_IPS.put("192.168.100.9", 1);
//        WEIGHT_IPS.put("192.168.100.10", 1);
    }
}
