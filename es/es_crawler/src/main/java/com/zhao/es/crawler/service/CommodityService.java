package com.zhao.es.crawler.service;

import com.alibaba.fastjson.JSON;
import com.zhao.es.crawler.entity.Commodity;
import com.zhao.es.crawler.util.JsoupUtil;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CommodityService {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 1、从jd爬取数据解析后放入es
     * @param keyword
     */
    public void crawl(String keyword) {
        try {
            List<Commodity> commodityList = JsoupUtil.parse(keyword);
            BulkRequest bulkRequest = new BulkRequest();
            bulkRequest.timeout("2m");

            for (Commodity commodity : commodityList) {
                bulkRequest.add(new IndexRequest("commodity").source(JSON.toJSONString(commodity), XContentType.JSON));
            }

            BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            System.out.println(response.hasFailures());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 搜索
     */
    public List<Map<String, Object>> search(String keyword, int pageNo, int pageSize, boolean isHighlight) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            // 执行查询请求
            SearchRequest searchRequest = getSearchRequest(keyword, pageNo, pageSize, isHighlight);
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

            // 解析结果
//            for (SearchHit hit : response.getHits().getHits()) {
//                list.add(getSourceAsMap(hit, isHighlight));
//            }
            list = Arrays.stream(response.getHits().getHits()).map(h -> getSourceAsMap(h, isHighlight)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private SearchRequest getSearchRequest(String keyword, int pageNo, int pageSize, boolean isHighlight) {
        pageNo = pageNo < 1 ? 1 : pageNo;
        // 精准匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", keyword);

        // 构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);
        searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        if (isHighlight) {
            // 高亮
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("name");
            highlightBuilder.requireFieldMatch(false); // 取消多个高亮显示
            highlightBuilder.preTags("<span style='color'>");
            highlightBuilder.postTags("</span>");
            searchSourceBuilder.highlighter(highlightBuilder);
        }

        // 构建查询请求
        SearchRequest searchRequest = new SearchRequest("commodity");
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }

    public Map<String, Object> getSourceAsMap(SearchHit hit, boolean isHighlight) {
        // 查询结果Map
        Map<String, Object> sourceAsMap = hit.getSourceAsMap();

        // 判断是否需要高亮显示
        if (isHighlight) {
            // 获取高亮显示字段Map
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            // 获取高亮字段
            HighlightField name = highlightFields.get("name");
            if (name != null) {
                String highlightName = "";
                // 遍历高亮字段
                for (Text fragment : name.fragments()) {
                    highlightName += fragment;
                }
                // 替换Map中高亮字段原来的值
                sourceAsMap.put("name", highlightName);
            }
        }

        return sourceAsMap;
    }
}
