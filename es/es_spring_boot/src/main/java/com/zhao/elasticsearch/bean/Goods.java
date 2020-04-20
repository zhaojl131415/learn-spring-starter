package com.zhao.elasticsearch.bean;


import lombok.*;
import org.elasticsearch.index.VersionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Document(indexName = "goods", type = "_doc", shards = 1, replicas = 0, createIndex = false, useServerConfiguration = true, versionType = VersionType.EXTERNAL)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Goods {

    @Id
    private long id;

    @Field(type = FieldType.Keyword)
    private String sn;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Double)
    private double price;

    @Field(type = FieldType.Integer)
    private int num;

    @Field(type = FieldType.Keyword)
    private int alert_num;

    @Field(type = FieldType.Keyword)
    private String image;

    @Field(type = FieldType.Keyword)
    private String images;

    @Field(type = FieldType.Double)
    private double weight;

    @Field(type = FieldType.Date)
    private String create_time;

    @Field(type = FieldType.Date)
    private String update_time;

    @Field(type = FieldType.Keyword)
    private String spu_id;

    @Field(type = FieldType.Integer)
    private int category_id;

    @Field(type = FieldType.Text)
    private String category_name;

    @Field(type = FieldType.Keyword)
    private String brand_name;

    @Field(type = FieldType.Text)
    private String spec;

    @Field(type = FieldType.Integer)
    private int sale_num;

    @Field(type = FieldType.Integer)
    private int comment_num;

    @Field(type = FieldType.Integer)
    private int status;

    @Version
    private Long version;
}