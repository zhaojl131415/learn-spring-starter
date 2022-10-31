package com.zhao.guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/9/9
 */
public class BiMapDemo {

    public static void main(String[] args) {
        // 创建 BiMap 映射
        BiMap<String, String> bimap = HashBiMap.create();
        bimap.put("张三", "12345@qq.com");
        bimap.put("李四", "345678@qq.com");
        bimap.put("王五", "348@qq.com");
        System.out.println(bimap);
        System.out.println("---------------键值反转");
        // inverse() 键和值进行反转
        System.out.println(bimap.inverse());
        System.out.println(bimap.inverse().get("12345@qq.com"));
    }
}
