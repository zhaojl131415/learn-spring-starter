package com.zhao.shardingSphere.spiDemo;

/**
 * @author ：楼兰
 * @date ：Created in 2021/1/7
 * @description:
 **/

public class Dog implements Animal{
    @Override
    public void noise() {
        System.out.println("wang wang wang");
    }
}
