package com.zhao.random;

import java.util.ArrayList;

import com.zhao.ServerIps;

import java.util.List;
import java.util.Random;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-14 20:51
 */
public class RandomWeight {

    public static String getServerIp() {
        List<String> list = new ArrayList<>();
        ServerIps.WEIGHT_IPS.forEach((k, v) -> {
            for (int i = 0; i < v; i++) {
                list.add(k);
            }
        });


        Random rand = new Random();
        int num = rand.nextInt(list.size());
        return list.get(num);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServerIp());
        }
    }
}
