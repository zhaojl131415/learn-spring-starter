package com.zhao.nio.demo;

import java.nio.ByteBuffer;

public class Demo9 {
    public static void main(String[] args) throws Exception {
        byte[] bytes=new byte[]{'a','b','c'};
        // 通过wrap方式包装byte[], 创建一个ByteBuffer, 对byte[]的修改也会影响ByteBuffer
        ByteBuffer byteBuffer=ByteBuffer.wrap(bytes);
        bytes[0]='b';
        byteBuffer.put(2,(byte)'b');
        for(int i=0;i<byteBuffer.capacity();i++){
            System.out.println((char)byteBuffer.get());
        }
    }
}
