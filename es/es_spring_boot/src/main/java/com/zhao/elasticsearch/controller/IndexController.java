package com.zhao.elasticsearch.controller;

import co.elastic.clients.elasticsearch.indices.IndexState;
import com.zhao.elasticsearch.service.impl.ElasticsearchIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * 索引操作 控制器
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/12/23
 */
@RestController
@RequestMapping("/index")
public class IndexController {


    @Autowired
    private ElasticsearchIndexService elasticsearchIndexService;


    @PostMapping("/create/{name}")
    public void create(@PathVariable String name) {
        try {
            elasticsearchIndexService.create(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/get/{name}")
    public void get(@PathVariable String name) {
        try {
            // 查询Index
            Map<String, IndexState> map = elasticsearchIndexService.get(name);
            System.out.println(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/exists/{name}")
    public void exists(@PathVariable String name) {
        try {
            // 判断index是否存在
            boolean exists = elasticsearchIndexService.exists(name);
            System.out.println(exists);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/delete/{name}")
    public void delete(@PathVariable String name) {
        try {
            // 删除index
            elasticsearchIndexService.delete(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
