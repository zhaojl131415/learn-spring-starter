package com.zhao.random;

import com.zhao.ServerIps;

import java.util.Random;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-14 20:41
 */
public class RandomNormal {

    public static String getServerIp() {
        Random rand = new Random();
        int num = rand.nextInt(ServerIps.IPS.size());
        return ServerIps.IPS.get(num);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServerIp());
        }
    }
}
