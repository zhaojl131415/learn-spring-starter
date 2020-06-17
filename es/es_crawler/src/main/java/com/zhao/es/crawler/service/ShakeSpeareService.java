package com.zhao.es.crawler.service;

import com.zhao.es.crawler.DTO.ScrollDTO;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-17 22:59
 */
@Service
public class ShakeSpeareService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 搜索
     */
    public ScrollDTO searchScroll(String keyword, String scrollId, int size) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            String[] includes = new String[]{"play_name", "line_number", "speaker", "text_entry"};
            String indices = "shakespeare";
            // 精准匹配
//            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", keyword);
            BoolQueryBuilder boolQueryBuilder = StringUtils.isEmpty(keyword) ? null :
                    QueryBuilders.boolQuery().must(QueryBuilders.termQuery("speaker", keyword));

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


    private SearchResponse getResponse(BoolQueryBuilder queryBuilder, String indices, String[] includes, String scrollId, int size) throws Exception {
        if (queryBuilder == null && scrollId != null) {
            SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId).scroll(TimeValue.MINUS_ONE);
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
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

            // 构建查询请求
            SearchRequest searchRequest = new SearchRequest().indices(indices).source(searchSourceBuilder);
            //size 为0不能使用scroll，否则会抛异常
            if (size != 0) {
                searchRequest.scroll(TimeValue.MINUS_ONE);
            }
            return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        }
    }
}
