package com.zhao.elasticsearch.mapper;

import com.zhao.elasticsearch.bean.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemMapper extends ElasticsearchRepository<Item,String>{



}
