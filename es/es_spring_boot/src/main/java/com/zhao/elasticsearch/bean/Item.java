package com.zhao.elasticsearch.bean;

import lombok.*;
import org.elasticsearch.index.VersionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;


@Document(indexName = "test_springdata_es",type = "_doc",shards = 1,replicas = 0,
        createIndex = true,useServerConfiguration = false,versionType = VersionType.EXTERNAL)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {

    @Id
    private long id;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private String sex;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Keyword)
    private List<String> interest;

    @Field(type = FieldType.Object)
    private ChildItem childItem;


}
