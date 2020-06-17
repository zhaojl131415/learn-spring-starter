package com.zhao.crawler.jd.entity;

import lombok.*;

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
