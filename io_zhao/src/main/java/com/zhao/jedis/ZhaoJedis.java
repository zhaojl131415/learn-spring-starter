package com.zhao.jedis;

import static com.zhao.jedis.ZhaoProtocol.*;
/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-04 15:05
 */
public class ZhaoJedis {

    private ZhaoJedisSocket jedisSocket;

    public ZhaoJedis(String ip, int port) {
        jedisSocket = new ZhaoJedisSocket(ip, port);
    }

    public String set(String key, String value) {
        jedisSocket.send(getCommand(Command.SET, key.getBytes(), value.getBytes()));
        return jedisSocket.read();
    }


    public String get(String key) {
        jedisSocket.send(getCommand(Command.GET, key.getBytes()));
        return jedisSocket.read();
    }

    private static String getCommand(Command command, byte[]... bytes){
        StringBuffer sb = new StringBuffer();
        sb.append(PREFIX_GROUP).append(bytes.length + 1).append(LINE);
        sb.append(PREFIX_COMMANND_LENGTH).append(command.toString().length()).append(LINE);
        sb.append(command.toString()).append(LINE);
        for (byte[] b : bytes) {
            sb.append(PREFIX_COMMANND_LENGTH).append(b.length).append(LINE);
            sb.append(new String(b)).append(LINE);
        }

        return sb.toString();
    }
}
