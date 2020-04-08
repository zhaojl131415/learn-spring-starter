package com.zhao.nio.demo;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-04 16:14
 */
public class Demo1 {

    public static void main(String[] args) {
        // 实例化一个IntBuffer, 指定最大容量capacity和限制limit都为8. 堆内缓冲
        IntBuffer buffer = IntBuffer.allocate(8);
        // 遍历buffer最大容量
        for (int i = 0; i < buffer.capacity(); i++) {
            // 生成20以内的随机数
            int nextInt = new SecureRandom().nextInt(20);
            // 写入进buffer
            buffer.put(nextInt);
        }
        // 循环结束, buffer的position也为8
        // 调用flip方法将当前position赋值给limit, 而position设置为0, 从头开始读取
        buffer.flip();
        // 判断position 是否 小于 limit
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
