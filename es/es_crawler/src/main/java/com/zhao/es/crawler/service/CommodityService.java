package com.zhao.es.crawler.service;

import com.alibaba.fastjson.JSON;
import com.zhao.es.crawler.DTO.ScrollDTO;
import com.zhao.es.crawler.entity.Commodity;
import com.zhao.es.crawler.util.JsoupUtil;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private RestHighLevelClient restHighLevelClient;

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

            BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
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
            SearchResponse response = getResponse(keyword, pageNo, pageSize, isHighlight);

            // 解析结果
//            for (SearchHit hit : response.getHits().getHits()) {
//                list.add(getSourceAsMap(hit, isHighlight));
//            }
            list = Arrays.stream(response.getHits().getHits()).map(h -> getSourceAsMap(h, isHighlight)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 搜索
     */
    public ScrollDTO searchScroll(String keyword, String scrollId, int size) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            String[] includes = new String[]{"name", "price", "shop"};
            String indices = "commodity";
            // 精准匹配
//            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", keyword);
            BoolQueryBuilder boolQueryBuilder = StringUtils.isEmpty(keyword) ? null :
                    QueryBuilders.boolQuery().must(QueryBuilders.termQuery("name", keyword));

            SearchResponse response = getResponse(boolQueryBuilder, indices, includes, null, size);
            if (response != null) {
                scrollId = response.getScrollId();
                list = Arrays.stream(response.getHits().getHits()).map(SearchHit::getSourceAsMap).collect(Collectors.toList());
            }

            if(scrollId != null){
                //清除滚屏
                ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
                //也可以选择setScrollIds()将多个scrollId一起使用
                clearScrollRequest.addScrollId(scrollId);
                restHighLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ScrollDTO(scrollId, list);
    }

    private SearchResponse getResponse(String keyword, int pageNo, int pageSize, boolean isHighlight) throws Exception {
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

        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
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

    private SearchResponse getResponse(BoolQueryBuilder queryBuilder, String indices, String[] includes, String scrollId, int size) throws Exception {
        TimeValue timeValue = TimeValue.timeValueMinutes(1L);

        if (queryBuilder == null && scrollId != null) {
            SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId).scroll(timeValue);
            return restHighLevelClient.scroll(searchScrollRequest, RequestOptions.DEFAULT);
        } else {
            // 构建查询条件
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(queryBuilder);
            searchSourceBuilder.size(size);
            //传了查询过滤字段才过滤结果，否则取全部字段
            if (includes != null && includes.length > 0) {
                //设置要获取的字段和不要获取的字段
                searchSourceBuilder.fetchSource(includes, new String[]{});
            }
            searchSourceBuilder.timeout(timeValue);

            // 构建查询请求
            SearchRequest searchRequest = new SearchRequest().indices(indices).source(searchSourceBuilder);
            //size 为0不能使用scroll，否则会抛异常
            if (size != 0) {
                searchRequest.scroll(timeValue);
            }
            return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        }
    }
}
