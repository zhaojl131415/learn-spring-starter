package com.zhao.elasticsearch.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.*;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.util.ObjectBuilder;
import com.fasterxml.jackson.databind.ser.PropertyBuilder;
import com.zhao.elasticsearch.annotations.Field;
import com.zhao.elasticsearch.service.IndexService;
import jakarta.json.stream.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/12/23
 */
@Service
@Slf4j
public class IndexServiceImpl implements IndexService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    /**
     * 新建索引，指定索引名称
     *
     * @param name
     * @throws IOException
     */
    @Override
    public void create(String name) throws IOException {
        CreateIndexResponse response = elasticsearchClient.indices().create(c -> c.index(name));
        log.info("createIndex方法，acknowledged={}", response.acknowledged());
    }

    /**
     * 新建索引，指定索引名称
     *
     * @param name
     * @param mapping
     * @throws IOException
     */
    @Override
    public void create(String name, String mapping) throws IOException {
        JsonpMapper mapper = elasticsearchClient._transport().jsonpMapper();
        JsonParser parser = mapper.jsonProvider().createParser(new StringReader(mapping));
        CreateIndexResponse response = elasticsearchClient.indices().create(c ->
                c.index(name).mappings(TypeMapping._DESERIALIZER.deserialize(parser, mapper))
        );
        log.info("createIndex方法，acknowledged={}", response.acknowledged());
    }

    /**
     * 自定义注解: 文档类型创建
     *
     * @param entity
     * @return
     */
    public <T> Map toMapping(T entity) {
        java.lang.reflect.Field[] fields = entity.getClass().getDeclaredFields();
        Map<String, Object> mapping = new HashMap<>();
        Map<String, Object> fieldMap = new HashMap<>();
        mapping.put("properties", fieldMap);
        Stream.of(fields).forEach(field -> {
            if (field.isAnnotationPresent(Field.class)) {
                Field annotation = field.getAnnotation(Field.class);
                Map<String, Object> fieldType = new HashMap<>();
                String name = StringUtils.hasLength(annotation.name()) ? annotation.name() : field.getName();
                fieldMap.put(name, fieldType);
                fieldType.put("type", annotation.type().name());
                fieldType.put("analyzer", annotation.analyzer());
//                fieldType.put("search_analyzer", annotation.searchAnalyzer());
//                fieldType.put("index", annotation.index());
            } else {
//                fieldMap.put(field.getName(), FieldType.A);
            }
        });
        return mapping;
    }

    /**
     * 创建索引，指定索引名称和setting和mapping
     *
     * @param name      - 索引名称
     * @param settingFn - 索引参数
     * @param mappingFn - 索引结构
     * @throws IOException
     */
    @Override
    public void create(String name, Function<IndexSettings.Builder, ObjectBuilder<IndexSettings>> settingFn, Function<TypeMapping.Builder, ObjectBuilder<TypeMapping>> mappingFn) throws IOException {
        CreateIndexResponse response = elasticsearchClient.indices().create(c -> c.index(name).settings(settingFn).mappings(mappingFn));
        log.info("createIndex方法，acknowledged={}", response.acknowledged());
    }

    /**
     * 删除索引
     *
     * @param name
     * @throws IOException
     */
    @Override
    public void delete(String name) throws IOException {
        DeleteIndexResponse response = elasticsearchClient.indices().delete(c -> c.index(name));
        log.info("deleteIndex方法，acknowledged={}", response.acknowledged());
    }

    /**
     * 修改索引字段信息 <br/>
     * 字段可以新增，已有的字段只能修改字段的 search_analyzer 属性。
     *
     * @param name        - 索引名称
     * @param propertyMap - 索引字段，每个字段都有自己的property
     * @throws IOException
     */
    @Override
    public void updateProperty(String name, Map<String, Property> propertyMap) throws IOException {
        PutMappingResponse response = elasticsearchClient.indices()
                .putMapping(typeMappingBuilder ->
                        typeMappingBuilder
                                .index(name)
                                .properties(propertyMap)
                );
        log.info("updateIndexMapping方法，acknowledged={}", response.acknowledged());
    }

    /**
     * 查询索引列表
     *
     * @return
     * @throws IOException
     */
    @Override
    public GetIndexResponse list() throws IOException {
        //使用 * 或者 _all都可以
        GetIndexResponse response = elasticsearchClient.indices().get(builder -> builder.index("_all"));
        log.info("getIndexList方法，response.result()={}", response.result().toString());
        return response;
    }

    /**
     * 查询索引详情
     *
     * @param name
     * @return
     * @throws IOException
     */
    @Override
    public GetIndexResponse get(String name) throws IOException {
        GetIndexResponse response = elasticsearchClient.indices().get(builder -> builder.index(name));
        log.info("getIndexDetail方法，response.result()={}", response.result().toString());
        return response;
    }

    /**
     * 检查指定名称的索引是否存在
     *
     * @param name
     * @return - true：存在
     * @throws IOException
     */
    @Override
    public boolean exists(String name) throws IOException {
        return elasticsearchClient.indices().exists(b -> b.index(name)).value();
    }
}
