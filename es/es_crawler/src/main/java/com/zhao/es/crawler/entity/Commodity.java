package com.zhao.es.crawler.entity;

import lombok.*;
import org.elasticsearch.index.VersionType;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "commodity", type = "_doc", shards = 5, replicas = 2, createIndex = false, useServerConfiguration = true, versionType = VersionType.EXTERNAL)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Commodity {
    private String name;

    private String shop;

    private String price;

    private String image;

    private String herf;
}
