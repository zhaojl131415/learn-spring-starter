package com.zhao.nio.demo;

import java.nio.ByteBuffer;

public class Demo7 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer=ByteBuffer.allocate(10);
        for(int i=0;i<byteBuffer.capacity();i++){
            byteBuffer.put((byte)i);
        }
        /**
         * ByteBuffer为抽象类, 实现类有四个:
         * @see java.nio.DirectByteBuffer     堆外: 性能更好, 比堆内的少了一步拷贝. 只有对象实例在jvm里, 数据byte[]在堆外, 对象内有个成员变量address指向操作系统堆外的一块内存空间.
         * @see java.nio.DirectByteBufferR    堆外只读
         * @see java.nio.HeapByteBuffer       堆内: 底层实现就是数据存储在堆内的byte[], 这个对象实例和数据byte[]都在堆内, 如果想操作这个数据, 需要在操作系统堆外开辟一块内存, 然后把这个byte[]拷贝过来
         * @see java.nio.HeapByteBufferR      堆内只读
         * DirectByteBufferR/HeapByteBufferR这两个以R结尾的, 表示只读的, 这两个实现类的put方法直接抛异常.
         */
        // 复制一个只读的新ByteBuffer
        ByteBuffer byteBuffer1 = byteBuffer.asReadOnlyBuffer();
        System.out.println(byteBuffer.getClass());
        System.out.println(byteBuffer1.getClass());
//        byteBuffer1.put((byte)10);
        byteBuffer1.flip();
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer1.position());
        for(int i=0;i<byteBuffer1.capacity();i++){
            System.out.println(byteBuffer1.get());
        }
    }
}
