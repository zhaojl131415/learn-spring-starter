package com.zhao.es.crawler.entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-17 22:55
 */
@Document(indexName = "shakespeare")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShakeSpeare {
    private String type;
    private int line_id;
    private String play_name;
    private String speech_number;
    private int line_number;
    private String speaker;
    private String text_entry;
}
