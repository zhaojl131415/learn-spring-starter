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
            // 从readChannel通道读数据, 写入buffer
            int read = readChannel.read(buffer);
            System.out.println(read);
            // 如果为-1, 表示readChannel没有读到数据
            if (-1 == read) {
                break;
            }
            // 把limit = position, 然后position设置为0
            buffer.flip();
            // 这里是从buffer读数据, 往通道写数据, 但是对于buffer而言是读, 可以理解为写是要从buffer里先把数据读出来,
            // 前面虽然已经把position设置为0, 但是这里一读, 又给读到调用flip之前的position的位置, 即position==limit, 等于没有归零
            writeChannel.write(buffer);


            // 此时 position==limit, buffer中不能写入数据, 原则是: position<=limit<=capacity
            // 所以下一次循环之前必须buffer.clear(), 否则从通道内读取数据的read方法永远返回零
            // 返回零的几种情况
            // 1. ByteBuffer已经写满了, 不能再写入了
            // 2. Channel中没有数据可读, 迭代的时候没有删除该SelectionKey可能会出现此种情况
            // 3. 网卡资源被其他socket占用
        }

        fis.close();
        fos.close();
    }
}
