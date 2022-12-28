package com.zhao.elasticsearch.controller;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhao.elasticsearch.bean.Goods;
import com.zhao.elasticsearch.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/12/23
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    public final static String INDEX = "goods";

    @Autowired
    private DocumentService documentService;


    @PostMapping("/create/{id}")
    public void create(@PathVariable String id) {
        try {
            Goods goods = new Goods(id, "iPhone 14 Pro Max", "iPhone 14 苹果手机", 6888.00, 10000, 100, "image", "images", 58.8, "2022-12-23", "2022-12-23", "1", 1, "手机", "Apple", "spec", 80, 100, 1, 1L);
//            IndexResponse indexResponse = documentService.create(INDEX, id, goods);
//            IndexResponse indexResponse = documentService.createByBuilderPattern(INDEX, id.toString(), goods);
            IndexResponse indexResponse = documentService.createByJson(INDEX, id.toString(), JSON.toJSONString(goods));
            System.out.println("response.forcedRefresh() -> " + indexResponse.forcedRefresh());
            System.out.println("response.toString() -> " + indexResponse.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/create/async/{id}")
    public void createAsync(@PathVariable String id) {
        try {
            Goods goods = new Goods(id, "iPhone 14 Pro Max", "iPhone 14 苹果手机", 6888.00, 10000, 100, "image", "images", 58.8, "2022-12-23", "2022-12-23", "1", 1, "手机", "Apple", "spec", 80, 100, 1, 1L);
            documentService.createAsync(INDEX, id, goods, (indexResponse, throwable) -> {
                // throwable必须为空
                Assert.isNull(throwable, throwable.getMessage());
//              Assertions.assertNull(throwable);
                // 验证结果
                System.out.println("response.toString() -> " + indexResponse.toString());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/update/{id}")
    public void update(@PathVariable String id) {
        try {
            Goods goods = new Goods(id, "iPhone 14 Pro", "iPhone 14 苹果手机", 6888.00, 10000, 100, "image", "images", 58.8, "2022-12-23", "2022-12-23", "1", 1, "手机", "Apple", "spec", 80, 100, 1, 1L);
            UpdateResponse<Goods> updateResponse = documentService.update(INDEX, id, goods, Goods.class);
            System.out.println(updateResponse.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/exists/{id}")
    public void exists(@PathVariable String id) {
        try {
            Boolean exists = documentService.exists(INDEX, id);
            System.out.println(exists);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/get/{id}")
    public void get(@PathVariable String id) {
        try {
//            Object goods = documentService.getById(INDEX, id);
//            ObjectNode goods = documentService.getObjectNodeById(INDEX, id);
            Goods goods = documentService.getById(INDEX, id, Goods.class);
            System.out.println(goods);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/search/{keyword}")
    public void search(@PathVariable String keyword) {
        try {
            List<Hit<Goods>> list = documentService.search(INDEX,
                    q -> q.match(t -> t.field("name").query(keyword)),
                    f -> f.field(o -> o.field("price").order(SortOrder.Desc)),
                    0, 3, Goods.class);
            for (Hit<Goods> hit : list) {
                System.out.println(hit.source());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/bulk")
    public void bulk() {
        try {
            List<Goods> list = new ArrayList<>();
            list.add(new Goods("2", "iPhone 14", "iPhone 14 苹果手机", 6588.00, 10000, 100, "image", "images", 58.8, "2022-12-23", "2022-12-23", "1", 1, "手机", "Apple", "spec", 80, 100, 1, 1L));
            list.add(new Goods("3", "iPhone 13", "iPhone 14 苹果手机", 6388.00, 10000, 100, "image", "images", 58.8, "2022-12-23", "2022-12-23", "1", 1, "手机", "Apple", "spec", 80, 100, 1, 1L));
            list.add(new Goods("4", "iPhone 12", "iPhone 14 苹果手机", 5888.00, 10000, 100, "image", "images", 58.8, "2022-12-23", "2022-12-23", "1", 1, "手机", "Apple", "spec", 80, 100, 1, 1L));
            list.add(new Goods("5", "iPhone 11", "iPhone 14 苹果手机", 4888.00, 10000, 100, "image", "images", 58.8, "2022-12-23", "2022-12-23", "1", 1, "手机", "Apple", "spec", 80, 100, 1, 1L));
            list.add(new Goods("6", "iPhone 10", "iPhone 14 苹果手机", 3888.00, 10000, 100, "image", "images", 58.8, "2022-12-23", "2022-12-23", "1", 1, "手机", "Apple", "spec", 80, 100, 1, 1L));

            BulkResponse bulkResponse = documentService.bulkCreate(INDEX, list);
            List<BulkResponseItem> items = bulkResponse.items();
            for (BulkResponseItem item : items) {
                System.out.println("BulkResponseItem.toString() -> " + item.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        try {
            Boolean flag = documentService.deleteById(INDEX, id);
            System.out.println(flag);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/delete/bulk/{ids}")
    public void bulkDelete(@PathVariable String ids) {
        try {
            BulkResponse bulkResponse = documentService.bulkDeleteByIds(INDEX, Arrays.asList(ids.split(",")));
            List<BulkResponseItem> items = bulkResponse.items();
            for (BulkResponseItem item : items) {
                System.out.println("BulkResponseItem.toString() -> " + item.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
