package com.zhao.nio.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-04 16:26
 */
public class Demo2 {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("/Users/zhaojinliang/read.txt");

        FileChannel channel = fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // channel读取的数据放入buffer
        channel.read(buffer);
        buffer.flip();
        while (buffer.remaining() > 0) {
            System.out.println((char)buffer.get());
        }
        fis.close();
    }
}
