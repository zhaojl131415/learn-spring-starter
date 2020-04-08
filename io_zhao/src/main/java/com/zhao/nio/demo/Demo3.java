package com.zhao.nio.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-04 16:26
 */
public class Demo3 {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream("/Users/zhaojinliang/write.txt");

        FileChannel channel = fos.getChannel();

        byte[] bytes = "zhao jin liang".getBytes();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put(bytes);

        buffer.flip();
        // buffer的数据写入channel
        channel.write(buffer);

        fos.close();
    }
}
