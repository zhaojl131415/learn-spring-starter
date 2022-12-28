package com.zhao.elasticsearch.controller;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import co.elastic.clients.elasticsearch.indices.IndexState;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.util.ObjectBuilder;
import com.zhao.elasticsearch.service.IndexService;
import jakarta.json.stream.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.function.Function;

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
    private IndexService indexService;


    @PostMapping("/create/{name}")
    public void create(@PathVariable String name) {
        try {
            indexService.create(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/create1/{name}")
    public void create1(@PathVariable String name) {
        try {
            //        Map<String, Property> map = new HashMap<>();
//        map.put("age", Property.of(o -> {}));
//        TypeMapping mapping = new TypeMapping.Builder().properties(toMapping()).build();

            String mappings = "{\"properties\":{" +
                    "\"alert_num\":{\"type\":\"long\"}," +
                    "\"brand_name\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}}," +
                    "\"category_id\":{\"type\":\"long\"}," +
                    "\"category_name\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}}," +
                    "\"comment_num\":{\"type\":\"long\"}," +
                    "\"create_time\":{\"type\":\"date\"}," +
                    "\"id\":{\"type\":\"long\"}," +
                    "\"image\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}}," +
                    "\"images\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}}," +
                    "\"name\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}}," +
                    "\"num\":{\"type\":\"long\"}," +
                    "\"price\":{\"type\":\"float\"}," +
                    "\"sale_num\":{\"type\":\"long\"}," +
                    "\"sn\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}}," +
                    "\"spec\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}}," +
                    "\"spu_id\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}}," +
                    "\"status\":{\"type\":\"long\"}," +
                    "\"update_time\":{\"type\":\"date\"}," +
                    "\"version\":{\"type\":\"long\"}," +
                    "\"weight\":{\"type\":\"float\"}" +
                    "}}";
            indexService.create(name, mappings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/create2/{name}")
    public void create2(@PathVariable String name) {
        try {
            // 构建setting
            Function<IndexSettings.Builder, ObjectBuilder<IndexSettings>> settingFn = sBuilder -> sBuilder
                    .index(iBuilder -> iBuilder
                            // 三个分片
                            .numberOfShards("3")
                            // 一个副本
                            .numberOfReplicas("1")
                    );

            // 索引字段，每个字段都有自己的property
            Property keywordProperty = Property.of(p -> p.keyword(k -> k.ignoreAbove(256)));
//        Property keywordProperty = new Property.Builder().keyword(new KeywordProperty.Builder().ignoreAbove(256).build()).build();

            Property integerProperty = Property.of(p -> p.integer(i -> i));
//        Property integerProperty = new Property.Builder().integer(new IntegerNumberProperty.Builder().build()).build();
            Property textProperty = Property.of(p -> p.text(tBuilder -> tBuilder));

            // 构建mapping
            Function<TypeMapping.Builder, ObjectBuilder<TypeMapping>> mappingFn = mBuilder -> mBuilder
                    .properties("name", keywordProperty)
                    .properties("age", integerProperty)
                    .properties("description", textProperty);
            indexService.create(name, settingFn, mappingFn);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/get/{name}")
    public void get(@PathVariable String name) {
        try {
            // 查询Index
            GetIndexResponse getIndexResponse = indexService.get(name);
            System.out.println(getIndexResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/exists/{name}")
    public void exists(@PathVariable String name) {
        try {
            // 判断index是否存在
            boolean exists = indexService.exists(name);
            System.out.println(exists);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/delete/{name}")
    public void delete(@PathVariable String name) {
        try {
            // 删除index
            indexService.delete(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
