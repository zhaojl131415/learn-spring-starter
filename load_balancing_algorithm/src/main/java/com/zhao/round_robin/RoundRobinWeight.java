package com.zhao.round_robin;

import com.zhao.ServerIps;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-14 21:08
 */
public class RoundRobinWeight {
    private static Integer request_index = 0;

    public static String getServerIp() {

        Integer total = ServerIps.WEIGHT_IPS.values().stream().reduce(Integer::sum).orElse(0);
        Integer num = request_index % total;
        request_index++;

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