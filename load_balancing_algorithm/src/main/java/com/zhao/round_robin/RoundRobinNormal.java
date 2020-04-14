package com.zhao.round_robin;

import com.zhao.ServerIps;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-14 21:08
 */
public class RoundRobinNormal {
    private static Integer index = 0;

    public static String getServerIp() {
        if (index > ServerIps.IPS.size()) {
            index = 0;
        }

        return ServerIps.IPS.get(index++);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServerIp());
        }
    }
}
