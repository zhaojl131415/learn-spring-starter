package com.zhao.guava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 布隆过滤器
 * guava的布隆过滤器只能单机使用, 需要使用分布式的还是得用redis的布隆过滤器
 *
 * redis布隆过滤器实现
 * @see com.zhao.redis.service.impl.BloomFilterServiceImpl
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/9/9
 */
public class BloomFilterDemo {

    public static void main(String[] args) {
        // 创建布隆过滤器对象: 容量: 1500，误判率: 0.01
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                1500,
                0.01);
        // 判断指定元素是否存在
        System.out.println(filter.mightContain(1));
        System.out.println(filter.mightContain(2));
        // 将元素添加进布隆过滤器
        filter.put(1);
        filter.put(2);
        System.out.println(filter.mightContain(1));
        System.out.println(filter.mightContain(2));
    }
}
