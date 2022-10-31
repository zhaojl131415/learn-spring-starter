package com.zhao.redis.service;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/9/9
 */
public interface BloomFilterService {
    /**
     * 添加数据
     *
     * @param key
     * @param data
     */
    void add(String key , String data);

    /**
     * 判断是否存在
     *
     * @param key
     * @param data
     * @return
     */
    Boolean exist(String key , String data);
}
