package com.spring.cloud.alibaba.sentinel.nacos;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.exception.NacosException;

import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Sentinel nacos写数据源实现
 *
 * @author zhaojinliang
 */
public class NacosWritableDataSource<T> implements WritableDataSource<T> {

    private final String serverAddr;
    private final String groupId;
    private final String dataId;
    private final Properties properties;
    private ConfigService configService;
    private final Converter<T, String> configEncoder;
    private final Lock lock = new ReentrantLock(true);

    public NacosWritableDataSource(String serverAddr, String groupId, String dataId, Converter<T, String> configEncoder) {
        this.serverAddr = serverAddr;
        this.groupId = groupId;
        this.dataId = dataId;
        this.properties = NacosWritableDataSource.buildProperties(serverAddr);
        this.configEncoder = configEncoder;
        initConfigService();
    }

    private void initConfigService() {
        try {
            this.configService = NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    private static Properties buildProperties(String serverAddr) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
        return properties;
    }

    @Override
    public void write(T t) throws Exception {
        lock.lock();
        try {
            // 发布配置
            configService.publishConfig(dataId, groupId, this.configEncoder.convert(t));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void close() throws Exception {

    }

}
