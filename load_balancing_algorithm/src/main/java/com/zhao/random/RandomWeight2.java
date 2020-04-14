package com.zhao.random;

import com.zhao.ServerIps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-14 20:56
 */
public class RandomWeight2 {

    public static String getServerIp() {
        Integer total = ServerIps.WEIGHT_IPS.values().stream().reduce(Integer::sum).orElse(0);

        Random rand = new Random();
        int num = rand.nextInt(total);

        String ip = "";
        for (String s : ServerIps.WEIGHT_IPS.keySet()) {
            Integer v = ServerIps.WEIGHT_IPS.get(s);
            if (num < v) {
                ip = s;
                break;
            }
            num = num - v;
        }
        return ip;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServerIp());
        }
    }
}
