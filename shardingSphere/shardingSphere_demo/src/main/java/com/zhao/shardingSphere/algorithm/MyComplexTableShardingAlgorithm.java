package com.zhao.shardingSphere.algorithm;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author roy
 * @desc
 */
public class MyComplexTableShardingAlgorithm implements ComplexKeysShardingAlgorithm<Long> {
    /**
     *
     * @param availableTargetNames 目标数据源 或者 表 的值。
     * @param shardingValue logicTableName逻辑表名 columnNameAndShardingValuesMap 分片列的精确值集合。 columnNameAndRangeValuesMap 分片列的范围值集合
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Long> shardingValue) {
        //实现按照 Between 进行范围分片。
        //例如 select * from course where cid in (1,3,5) and user_id Between 200 and 300;
        Collection<Long> cidCol = shardingValue.getColumnNameAndShardingValuesMap().get("cid");
        Range<Long> uageRange = shardingValue.getColumnNameAndRangeValuesMap().get("user_id");

        List<String> result = new ArrayList<>();

        Long uageLower = uageRange.lowerEndpoint();//200
        Long uageUpper = uageRange.upperEndpoint();//300
        //实现自定义分片逻辑 例如可以自己实现 course_$->{cid%2+1 + (30-20)+1} 这样的复杂分片逻辑
        if(uageUpper<uageLower){
            System.out.println("uageupper is less then uagelower");
            throw new UnsupportedOperationException("error param of user range");
        }
        if(uageUpper<=1000 || uageLower>2000){
            System.out.println("error param of user range");
            return result;
        }
        for(Long cid : cidCol){
            BigInteger cidI = BigInteger.valueOf(cid);
            BigInteger target = (cidI.mod(BigInteger.valueOf(2L))).add(new BigInteger("1"));
            result.add("course_"+target);
        }
        return result;
    }
}
