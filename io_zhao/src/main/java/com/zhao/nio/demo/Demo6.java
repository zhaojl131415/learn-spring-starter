package com.zhao.nio.demo;

import java.nio.ByteBuffer;

public class Demo6 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer=ByteBuffer.allocate(10);
        for(int i=0;i<byteBuffer.capacity();++i){
            byteBuffer.put((byte)i);
        }
        // mark() 标记position的位置, 通常与reset()一起用, reset()将position置为原mark位.
//        byteBuffer.mark();
        byteBuffer.position(2);
        byteBuffer.limit(8);
        // 复制一个从2-8的长度为6的新ByteBuffer, 内容是原byteBuffer的引用
        // 2 3 4 5 6 7
        ByteBuffer resetBuffer = byteBuffer.slice();
        for(int i=0;i<resetBuffer.capacity();i++){
            byte anInt = resetBuffer.get();
            // 遍历新的ByteBuffer, 给其中的值都乘以2, 改变这个buffer的内容, 会影响原buffer的内容
            // 4 6 8 10 12 14
            resetBuffer.put(i, (byte) (anInt*2));
        }

        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());
//        byteBuffer.reset();
        // 遍历老的ByteBuffer并输出, 其中position为2-8的值都乘以2了, 值引用
        // 0 1 2 3 4 5  6  7  8 9
        // 0 1 4 6 8 10 12 14 8 9
        while (byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }

    }
}
