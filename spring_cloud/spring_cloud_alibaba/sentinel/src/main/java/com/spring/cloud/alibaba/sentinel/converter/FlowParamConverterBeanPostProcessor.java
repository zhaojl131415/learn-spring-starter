package com.spring.cloud.alibaba.sentinel.converter;

import com.alibaba.cloud.sentinel.datasource.converter.JsonConverter;
import com.alibaba.cloud.sentinel.datasource.factorybean.NacosDataSourceFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 自定义后置处理器: 用于注册自定义解析器
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/10/26
 */
//@Component
public class FlowParamConverterBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private JsonConverter jsonParamFlowConverter;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // Sentinel热点参数规则配置Nacos数据源
        if (beanName.equals("param-flow-rules-sentinel-nacos-datasource")) {
            NacosDataSourceFactoryBean nacosDataSourceFactoryBean = (NacosDataSourceFactoryBean) bean;
            // 指定解析器
            nacosDataSourceFactoryBean.setConverter(jsonParamFlowConverter);
            return bean;
        }
        return bean;
    }
}