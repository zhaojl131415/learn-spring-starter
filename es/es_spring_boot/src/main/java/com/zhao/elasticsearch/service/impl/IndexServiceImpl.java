package com.zhao.elasticsearch.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.util.ObjectBuilder;
import com.zhao.elasticsearch.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.Function;

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
    public void createIndex(String name) throws IOException {
        CreateIndexResponse response = elasticsearchClient.indices().create(c -> c.index(name));
        log.info("createIndex方法，acknowledged={}", response.acknowledged());
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
    public void createIndex(String name, Function<IndexSettings.Builder, ObjectBuilder<IndexSettings>> settingFn, Function<TypeMapping.Builder, ObjectBuilder<TypeMapping>> mappingFn) throws IOException {
        CreateIndexResponse response = elasticsearchClient
                .indices()
                .create(c -> c
                        .index(name)
                        .settings(settingFn)
                        .mappings(mappingFn)
                );
        log.info("createIndex方法，acknowledged={}", response.acknowledged());
    }

    /**
     * 删除索引
     *
     * @param name
     * @throws IOException
     */
    @Override
    public void deleteIndex(String name) throws IOException {
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
    public void updateIndexProperty(String name, HashMap<String, Property> propertyMap) throws IOException {
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
    public GetIndexResponse getIndexList() throws IOException {
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
    public GetIndexResponse getIndexDetail(String name) throws IOException {
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
    public boolean indexExists(String name) throws IOException {
        return elasticsearchClient.indices().exists(b -> b.index(name)).value();
    }
}
