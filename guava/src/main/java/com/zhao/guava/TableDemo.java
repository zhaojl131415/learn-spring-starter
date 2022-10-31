package com.zhao.guava;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Set;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/9/9
 */
public class TableDemo {
    public static void main(String[] args) {
        Table<String, String, Integer> table = HashBasedTable.create();
        table.put("jack", "java", 30);
        table.put("black Sir", "PHP", 80);
        table.put("sindy", "javascript", 90);
        table.put("wang", "python", 100);
        // 通过行和列的key来获取表格中的值
        Integer s = table.get("jack", "java");
        System.out.println(s);
        // 获取表格集合
        Set<Table.Cell<String, String, Integer>> set = table.cellSet();
        // 遍历表格
        for (Table.Cell c : set) {
            System.out.println("行：" + c.getRowKey() + ",列：" + c.getColumnKey() + ",值：" + c.getValue());
        }
    }
}
