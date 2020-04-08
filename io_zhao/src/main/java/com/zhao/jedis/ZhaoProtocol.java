package com.zhao.jedis;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-04 15:11
 */
public class ZhaoProtocol {

    public static final String PREFIX_GROUP = "*";
    public static final String PREFIX_COMMANND_LENGTH = "$";
    public static final String LINE = "\r\n";

    public enum Command {
        SET,
        GET,
        INCR
    }
}
