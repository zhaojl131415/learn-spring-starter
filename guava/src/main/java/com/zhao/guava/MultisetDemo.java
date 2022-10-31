package com.zhao.guava;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.Set;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/9/9
 */
public class MultisetDemo {
    public static void main(String[] args) {
        String s = "good good study day day up";
        String[] ss = s.split(" ");
        // 创建一个Multiset 集合
        Multiset<String> mu = HashMultiset.create();
        for (String str : ss) {
            mu.add(str);
        }
        Set<String> st = mu.elementSet();
        // 查看每个单词所出现的次数
        st.forEach(v -> System.out.println(v + ":" + mu.count(v)));
    }
}
