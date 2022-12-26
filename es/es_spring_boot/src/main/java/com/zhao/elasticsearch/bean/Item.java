package com.zhao.elasticsearch.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private long id;

    private String name;

    private String sex;

    private Integer age;

    private List<String> interest;

    private ChildItem childItem;


}
