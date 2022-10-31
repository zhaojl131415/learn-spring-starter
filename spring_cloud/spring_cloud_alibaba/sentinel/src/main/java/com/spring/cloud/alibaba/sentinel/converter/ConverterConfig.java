package com.spring.cloud.alibaba.sentinel.converter;

import com.alibaba.cloud.sentinel.datasource.converter.JsonConverter;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 解析器配置: 注入一个JSON解析器
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/10/26
 */
//@Configuration
public class ConverterConfig {

    @Bean("sentinel-json-param-flow-converter2")
    @Primary
    public JsonConverter jsonParamFlowConverter() {
        return new FlowParamJsonConverter(new ObjectMapper(), ParamFlowRule.class);
    }
}
