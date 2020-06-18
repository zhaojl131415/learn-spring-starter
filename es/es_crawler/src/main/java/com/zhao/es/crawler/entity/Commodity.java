package com.zhao.es.crawler.entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "commodity")
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
