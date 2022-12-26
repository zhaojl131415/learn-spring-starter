package com.zhao.elasticsearch.service;

import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhao.elasticsearch.bean.IdBase;

import java.io.IOException;
import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/12/23
 */
public interface DocumentService {

    /**
     * 新增一个文档
     * @param idxName 索引名
     * @param idxId 索引id
     * @param document 文档对象
     * @return
     */
    IndexResponse create(String idxName, String idxId, Object document) throws Exception;

    /**
     * 新增一个文档
     * @param idxName 索引名
     * @param idxId 索引id
     * @param document 文档对象
     * @return
     */
    IndexResponse createByBuilderPattern(String idxName, String idxId, Object document) throws Exception;

    /**
     * 用JSON字符串创建文档
     * @param idxName 索引名
     * @param idxId 索引id
     * @param jsonContent
     * @return
     */
    IndexResponse createByJson(String idxName, String idxId, String jsonContent) throws Exception;

    /**
     * 异步新增文档
     * @param idxName 索引名
     * @param idxId 索引id
     * @param document
     * @param action
     */
    void createAsync(String idxName, String idxId, Object document, BiConsumer<IndexResponse, Throwable> action);

    /**
     * 批量增加文档
     * @param idxName 索引名
     * @param documents 要增加的对象集合
     * @return 批量操作的结果
     * @throws Exception
     */
    <E extends IdBase> BulkResponse bulkCreate(String idxName, Collection<E> documents) throws Exception;

    /**
     * 修改文档
     * @param idxName 索引名
     * @param idxId 索引id
     * @param document
     * @param clazz
     */
    <T> UpdateResponse<T> update(String idxName, String idxId, T document, Class<T> clazz) throws Exception;

    /**
     * 根据文档id判断文档是否存在
     * @param idxName 索引名
     * @param docId 文档id
     * @return Boolean
     * @throws Exception
     */
    Boolean exists(String idxName, String docId) throws IOException;

    /**
     * 根据文档id查找文档
     * @param idxName 索引名
     * @param docId 文档id
     * @return Object类型的查找结果
     * @throws Exception
     */
    Object getById(String idxName, String docId) throws IOException;

    /**
     * 根据文档id查找文档
     * @param idxName 索引名
     * @param docId 文档id
     * @return Object类型的查找结果
     * @throws Exception
     */
    <T> T getById(String idxName, String docId, Class<T> clazz) throws IOException;

    /**
     * 根据文档id查找文档，返回类型是ObjectNode
     * @param idxName 索引名
     * @param docId 文档id
     * @return ObjectNode类型的查找结果
     */
    ObjectNode getObjectNodeById(String idxName, String docId) throws IOException;

    /**
     * 根据文档id删除文档
     * @param idxName 索引名
     * @param docId 文档id
     * @return Boolean
     * @throws Exception
     */
    Boolean deleteById(String idxName, String docId) throws IOException;

    /**
     * 批量删除文档
     * @param idxName 索引名
     * @param docIds 要删除的文档id集合
     * @return
     * @throws Exception
     */
    BulkResponse bulkDeleteByIds(String idxName, Collection<String> docIds) throws Exception;
}
