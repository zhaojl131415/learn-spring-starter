package com.zhao.nio.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Demo8 {
    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/zhaojinliang/write.txt");
        FileInputStream fileInputStream = new FileInputStream("/Users/zhaojinliang/read.txt");

        FileChannel channelRead = fileInputStream.getChannel();
        FileChannel channelWrite = fileOutputStream.getChannel();
        /**
         * 这个和Demo4的例子, 只有这一行有区别:
         * allocate: 分配堆内buffer
         * allocateDirect: 分配堆外buffer
         */
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
        while (true) {
            byteBuffer.clear();
            int readNumber = channelRead.read(byteBuffer);
            System.out.println(readNumber);
            if (readNumber == -1) {
                break;
            }
            byteBuffer.flip();
            channelWrite.write(byteBuffer);
        }
        fileOutputStream.close();
        fileInputStream.close();
    }
}
