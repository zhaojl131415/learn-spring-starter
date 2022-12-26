package com.zhao.elasticsearch.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhao.elasticsearch.bean.IdBase;
import com.zhao.elasticsearch.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/12/23
 */
@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Autowired
    private ElasticsearchAsyncClient elasticsearchAsyncClient;

    @Override
    public IndexResponse create(String idxName, String idxId, Object document) throws Exception {
        return elasticsearchClient.index(idx -> idx.index(idxName).id(idxId).document(document));
    }

    @Override
    public IndexResponse createByBuilderPattern(String idxName, String idxId, Object document) throws Exception {
        IndexRequest.Builder<Object> indexReqBuilder = new IndexRequest.Builder<>().index(idxName).id(idxId).document(document);
        return elasticsearchClient.index(indexReqBuilder.build());
    }

    @Override
    public IndexResponse createByJson(String idxName, String idxId, String jsonContent) throws Exception {
        return elasticsearchClient.index(i -> i.index(idxName).id(idxId).withJson(new StringReader(jsonContent)));
    }

    @Override
    public void createAsync(String idxName, String idxId, Object document, BiConsumer<IndexResponse, Throwable> action) {
        elasticsearchAsyncClient.index(idx -> idx.index(idxName).id(idxId).document(document)).whenComplete(action);
    }

    @Override
    public <E extends IdBase> BulkResponse bulkCreate(String idxName, Collection<E> documents) throws Exception {
        List<BulkOperation> bulkList = documents.stream().map(d -> BulkOperation.of(o -> o.index(i -> i.id(d.getId()).document(d)))).collect(Collectors.toList());
        return elasticsearchClient.bulk(b -> b.index(idxName).operations(bulkList));

        // TODO 可以将 Object定义为一个文档基类。比如 ESDocument类

        // 将每一个product对象都放入builder中
//        documents.stream()
//                .forEach(esDocument -> br
//                        .operations(op -> op
//                                .index(idx -> idx
//                                        .index(idxName)
//                                        .id(esDocument.getId())
//                                        .document(esDocument))));

//        BulkRequest.Builder br = new BulkRequest.Builder().index(idxName).operations(bulkOperationArrayList);
//        return elasticsearchClient.bulk(br.build());
    }

    @Override
    public <T> UpdateResponse<T> update(String idxName, String idxId, T document, Class<T> clazz) throws Exception {
        return elasticsearchClient.update(u -> u.index(idxName).id(idxId).doc(document), clazz);
    }

    /**
     * 根据文档id判断文档是否存在
     *
     * @param idxName 索引名
     * @param docId   文档id
     * @return Boolean
     * @throws Exception
     */
    @Override
    public Boolean exists(String idxName, String docId) throws IOException {
        return elasticsearchClient.exists(e -> e.index(idxName).id(docId)).value();
    }

    @Override
    public Object getById(String idxName, String docId) throws IOException {
        GetResponse<Object> response = elasticsearchClient.get(g -> g.index(idxName).id(docId), Object.class);
        return response.found() ? response.source() : null;
    }

    @Override
    public <T> T getById(String idxName, String docId, Class<T> clazz) throws IOException {
        GetResponse<T> response = elasticsearchClient.get(g -> g.index(idxName).id(docId), clazz);
        return response.found() ? response.source() : null;
    }

    @Override
    public ObjectNode getObjectNodeById(String idxName, String docId) throws IOException {
        GetResponse<ObjectNode> response = elasticsearchClient.get(g -> g.index(idxName).id(docId), ObjectNode.class);

        return response.found() ? response.source() : null;
    }

    @Override
    public Boolean deleteById(String idxName, String docId) throws IOException {
        DeleteResponse delete = elasticsearchClient.delete(d -> d.index(idxName).id(docId));
        return delete.forcedRefresh();
    }

    @Override
    public BulkResponse bulkDeleteByIds(String idxName, Collection<String> docIds) throws Exception {

        List<BulkOperation> bulkList = docIds.stream().map(d -> BulkOperation.of(o -> o.delete(i -> i.id(d)))).collect(Collectors.toList());
        return elasticsearchClient.bulk(b -> b.index(idxName).operations(bulkList));

//        BulkRequest.Builder br = new BulkRequest.Builder();
//        // 将每一个对象都放入builder中
//        docIds.stream().forEach(id -> br
//                .operations(op -> op
//                        .delete(d -> d
//                                .index(idxName)
//                                .id(id))));
//        return elasticsearchClient.bulk(br.build());
    }
}
