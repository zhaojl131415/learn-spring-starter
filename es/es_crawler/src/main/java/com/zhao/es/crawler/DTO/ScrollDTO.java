package com.zhao.es.crawler.DTO;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-17 22:07
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScrollDTO {

    private String scrollId;

    private List<Map<String, Object>> list;
}
