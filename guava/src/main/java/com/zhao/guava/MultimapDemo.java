package com.zhao.guava;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import java.util.Iterator;
import java.util.Map;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/9/9
 */
public class MultimapDemo {
    public static void main(String[] args) {
        // 创建一个Multimap 映射
        Multimap<String, String> map = HashMultimap.create();

        map.put("张三", "Python从入门到实战");
        map.put("张三", "PHP从入门到实战");
        map.put("李四", "Javascript高级设计");
        map.put("王五", "大话设计模式");

//        Map<String, String> map1 = Maps.newHashMap();
//        map1.put("Python从入门到实战", "张三");
//        map1.put("PHP从入门到实战", "张三");
//        map1.put("Javascript高级设计", "李四");
//        map1.put("大话设计模式", "王五");
//        Iterator<Map.Entry<String, String>> iterator = map1.entrySet().iterator();
//        while(iterator.hasNext()) {
//            Map.Entry<String, String> entry = iterator.next();
//            map.put(entry.getValue(), entry.getKey());
//        }
        // map.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println(map);
        System.out.println(map.get("张三")); // [PHP从入门到实战, Python从入门到实战]
        System.out.println(map.get("李四")); // [Javascript高级设计]
        System.out.println(map.get("王五")); // [大话设计模式]




    }
}
