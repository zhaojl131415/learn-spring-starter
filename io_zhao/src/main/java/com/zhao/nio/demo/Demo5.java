package com.zhao.nio.demo;

import java.nio.ByteBuffer;

public class Demo5 {
    public static void main(String[] args) {
        ByteBuffer buffer=ByteBuffer.allocate(100);
        buffer.putChar('a');
        buffer.putInt(2);
        buffer.putLong(50000L);
        buffer.putShort((short) 2);
        buffer.putDouble(12.4);
        System.out.println(buffer.position());
        buffer.flip();
        // 如果不按put的类型取, 数据会不对
        System.out.println(buffer.getChar());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getDouble());
    }
}
