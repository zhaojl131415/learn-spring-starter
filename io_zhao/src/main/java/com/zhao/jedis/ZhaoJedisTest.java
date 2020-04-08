package com.zhao.jedis;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-04 15:14
 */
public class ZhaoJedisTest {
    public static void main(String[] args) {
        ZhaoJedis jedis = new ZhaoJedis("192.168.100.60", 6379);
        System.out.println(jedis.set("zhao", "123"));
        System.out.println(jedis.get("zhao"));
    }
}
