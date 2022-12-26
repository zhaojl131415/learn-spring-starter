package com.zhao.elasticsearch.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.IndexState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 索引操作 Service
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/12/23
 */
@Service
public class ElasticsearchIndexService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public void create(String name) throws IOException {
        elasticsearchClient.indices().create(c -> c.index(name));
    }

    public boolean exists(String name) throws IOException {
        return elasticsearchClient.indices().exists(b -> b.index(name)).value();
    }

    public Map<String, IndexState> get(String name) throws IOException {
        return elasticsearchClient.indices().get(i -> i.index(name)).result();
    }

    public void delete(String name) throws IOException {
        elasticsearchClient.indices().delete(c -> c.index(name));
    }
}
