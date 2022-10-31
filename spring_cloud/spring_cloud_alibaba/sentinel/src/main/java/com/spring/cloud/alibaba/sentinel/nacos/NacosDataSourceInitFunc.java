package com.spring.cloud.alibaba.sentinel.nacos;

import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @Author zhaojinliang
 */
public class NacosDataSourceInitFunc implements InitFunc {

    @Override
    public void init() throws Exception {
        //流控规则
        WritableDataSource<List<FlowRule>> writableDataSource = new NacosWritableDataSource<>(
                "127.0.0.1:8848", "DEFAULT_GROUP", "sentinel-flow", JSON::toJSONString);
        // 写入规则配置数据源, 调用Sentinel源码注册.
        WritableDataSourceRegistry.registerFlowDataSource(writableDataSource);
    }
}
