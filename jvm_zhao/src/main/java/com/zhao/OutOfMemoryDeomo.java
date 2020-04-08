package com.zhao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-08 15:27
 */
public class OutOfMemoryDeomo {

    /**
     * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./oomdump.dump
     * -Xmx10m -Xms10m -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -Xloggc:./parallel-gc.log
     * -Xmx10m -Xms10m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -Xloggc:./cms-gc.log
     * -Xmx10m -Xms10m -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -Xloggc:./g1-gc.log
     */
    public static void main(String[] args) {
        List<Object> list = new ArrayList<Object>();
        int i = 0, j = 0;
        while (true) {
            list.add(new User(i++, UUID.randomUUID().toString()));
            new User(j--, UUID.randomUUID().toString());
        }
    }
}

class User {
    private int id;
    private String token;

    public User(int id, String token) {
        this.id = id;
        this.token = token;
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", token='" + token + '\'' +
                '}';
    }
}
