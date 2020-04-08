package com.zhao.supports.sentinel;

import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.config.NacosConfigService;

import java.util.List;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-02 21:31
 */
public class NacosWritableDataSource<T> implements WritableDataSource<T> {


    final String remoteAddress = "localhost";
    final String groupId = "Sentinel:Demo";
    final String dataId = "com.alibaba.csp.sentinel.demo.flow.rule";


    @Override
    public void write(T t) throws Exception {
//        encodeJson();
//        NacosS
    }

    @Override
    public void close() throws Exception {

    }

    private <T> String encodeJson(T t) {
        return JSON.toJSONString(t);
    }
}
