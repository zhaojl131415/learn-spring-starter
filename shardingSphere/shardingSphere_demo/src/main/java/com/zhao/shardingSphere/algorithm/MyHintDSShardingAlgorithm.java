package com.zhao.shardingSphere.algorithm;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author ：楼兰
 * @description: 自定义扩展hint分片算法。Hint分片的分片键从SQL语句中抽离出来，由程序自行指定。
 * 通过HintManager来指定。注意这个HintManager设置的分片键都是线程安全的。
 **/

public class MyHintDSShardingAlgorithm implements HintShardingAlgorithm<Integer> {
    /**
     *
     * @param availableTargetNames 可选 数据源 和 表 的名称
     * @param shardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<Integer> shardingValue) {
        // 对SQL的零侵入分片方案。shardingValue是通过HintManager.
        // 比如我们要实现将 select * from t_user where user_id in {1,2,3,4,5,.....}; 按照in的第一个值，全部路由到course_1表中。
        // 注意他使用时有非常多的限制。
//        String key = "m"+shardingValue.getValues().toArray()[0];
//        if(availableTargetNames.contains(key)){
//            return Arrays.asList(key);
//        }
//        throw new UnsupportedOperationException(" route "+key+" is not supported. please check your config");
        return Arrays.asList("m1","m2");
    }
}
