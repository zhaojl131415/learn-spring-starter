package com.spring.cloud.alibaba.sentinel.converter;

import com.alibaba.cloud.sentinel.datasource.converter.JsonConverter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义解析器
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/10/26
 */
public class FlowParamJsonConverter extends JsonConverter {
    private Class ruleClass;

    public FlowParamJsonConverter(ObjectMapper objectMapper, Class ruleClass) {
        super(objectMapper, ruleClass);
        this.ruleClass = ruleClass;
    }

    @Override
    public Collection<Object> convert(String source) {
        List<Object> list = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(source);
        for (int i = 0; i < jsonArray.size(); i++) {
            //解析rule属性
            JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(i).get("rule");
            Object object = JSON.toJavaObject(jsonObject, ruleClass);
            list.add(object);
        }
        return list;
    }
}
