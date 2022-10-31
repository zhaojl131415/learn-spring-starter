package com.zhao.redis.service.impl;

import com.zhao.redis.service.BloomFilterService;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/9/9
 */
@Service
public class BloomFilterServiceImpl implements BloomFilterService {
    /**
     * 容量
     */
    protected Long expectedInsertions = 1000000L;
    /**
     * 误差
     */
    protected Double falseProbability = 0.001;

    @Autowired
    private Redisson redisson;

    /**
     * 添加数据
     *
     * @param key
     * @param data
     */
    @Override
    public void add(String key, String data) {
        RBloomFilter bloomFilter = redisson.getBloomFilter(key);
        bloomFilter.tryInit(expectedInsertions, falseProbability);
        bloomFilter.add(data);
    }

    /**
     * 判断是否存在
     *
     * @param key
     * @param data
     * @return
     */
    @Override
    public Boolean exist(String key, String data) {
        RBloomFilter bloomFilter = redisson.getBloomFilter(key);
        bloomFilter.tryInit(expectedInsertions , falseProbability);
        return bloomFilter.contains(data);
    }
}
