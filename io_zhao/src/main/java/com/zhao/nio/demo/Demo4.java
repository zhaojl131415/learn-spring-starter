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
public class Demo4 {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("/Users/zhaojinliang/read.txt");
        FileOutputStream fos = new FileOutputStream("/Users/zhaojinliang/write.txt");

        FileChannel readChannel = fis.getChannel();
        FileChannel writeChannel = fos.getChannel();


        ByteBuffer buffer = ByteBuffer.allocate(5);

        while (true) {
            // 清空buffer的位置, position设置为0, limit为capacity
            buffer.clear();
            // 如果buffer这里没有清空位置, position值为5, read永远都是返回0, 会死循环, 不会break, 一直读的就是 position:1-5的数据
            System.out.println("----position----" + buffer.position());
            int read = readChannel.read(buffer);
            System.out.println(read);
            if (-1 == read) {
                break;
            }
            // 把position设置为0
            buffer.flip();
            // 这里是往通道写数据, 但是对于buffer而言是读, 可以理解为写是要从buffer里先把数据读出来,
            // 前面虽然已经把position设置为0, 但是这里一读, 又给读到调用flip之前的position的位置, position又变成原来的值了, 等于没有归零
            writeChannel.write(buffer);
        }

        fis.close();
        fos.close();
    }
}
