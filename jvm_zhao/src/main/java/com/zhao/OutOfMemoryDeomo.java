package com.zhao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                byte[] bytes = new byte[10240];
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new String(bytes);
            }).start();
        }

        while (true) {
//            executorService.submit(() -> {
//
//                byte[] bytes = new byte[1024];
////                try {
////                    Thread.sleep(2000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//                new String(bytes);
//            });

            System.out.println("1111");
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
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
