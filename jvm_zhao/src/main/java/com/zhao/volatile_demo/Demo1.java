package com.zhao.volatile_demo;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-05-25 10:51
 */
public class Demo1 {
    // volatile
    static boolean running = true;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("test start...");
            while (running){
                 System.out.println("a");
            }
            System.out.println("test end...");
        },"t1").start();

        new Thread(() -> {
            running = false;
        },"t2").start();
//
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        running = false;
    }
}
