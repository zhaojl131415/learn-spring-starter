package com.zhao.volatile_demo;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/12/31
 */
public class VisibilityDemo {


    // volatile
    private boolean running = true;
//        private volatile boolean running = true;  // 1
    private int count = 0;
//    private volatile int count = 0; // 6
//    private Integer count = 0; // 7

    public void a() throws InterruptedException {
        System.out.println("test start...");
        while (running) {
            count++;
            /**
             * 保证可见性, 跳出循环的方式:
             * 1. 通过给running添加volatile可以跳出循环, volatile底层实现: storeLoad内存屏障中x86利用lock前缀汇编指令：8、深入理解volatile原理与使用.
             * 在JVM源码中, 当被lock前缀指令修饰的共享变量发生改变时, 会立即刷回主内存, 并使其他线程工作内存中的当前共享变量失效
             * 2. 通过内存屏障跳出循环: Unsafe.getUnsafe().storeFence(); 源码实现最终一样利用lock前缀指令.
             * 3. Thread.yield() 释放线程时间片. 线程的上下文切换, 线程切换之后, 原线程工作内存中的共享变量会失效(不确定是类似8的清理还是直接清理), 当还原线程时再从主内存中加载最新的值.
             * 4. 打印输出: System.out.println("a")
             * 原因:println()方法中的synchronized, synchronized保证可见性, 最终还是跟内存屏障有关, 源码实现也会调用2的storeFence,
             * 5. LockSupport.unpark(Thread.currentThread()); 内存屏障:storeFence
             * 6. 对count属性添加volatile可以跳出循环 ???
             * 7. 将count的int(基本数据类型)改为Integer(包装类), 因为包装类的value是final修饰的, final可以保证可见性, 可能也跟内存屏障有关
             * 8. 短时间等待, 不访问共享变量后, 工作内存会清理掉共享变量, 再访问时会从主内存中重新获取
             * 9. Thread.sleep(1) 内存屏障:storeFence
             *
             * 总结: 保证线程可见性归根结底只有两种方式: 1.内存屏障：lock/mfence指令 2.线程上下文切换
             *
             */
            // 通过内存屏障跳出循环
//            Unsafe.getUnsafe().storeFence(); // 2
//            Thread.yield(); // 3
//            System.out.println("a"); // 4
//            LockSupport.unpark(Thread.currentThread()); // 5
//            shortWait(10000000); // 8
//            Thread.sleep(1); // 9
        }
        System.out.println("test end...");
        System.out.println("count:"+count);
    }

    public void b() {
        running = false;
    }

    public static void main(String[] args) throws InterruptedException {
        VisibilityDemo d = new VisibilityDemo();
        new Thread(() -> {
            try {
                d.a();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        Thread.sleep(1000);
        new Thread(() -> d.b(), "t2").start();
    }

    /**
     * 短时间等待, 用来验证工作内存是否会清理
     * 其实就是用一个while循环来抢占执行, 不让线程调用共享变量, 当一段时间(不清楚具体多久)没有访问共享变量时, 工作内存中的共享变量会被清理掉, 再访问时会从主内存中重新获取
     * @param interval
     */
    public static void shortWait(long interval) {
        long start = System.nanoTime();
        long end;
        do {
            end = System.nanoTime();
        } while(start + interval >= end);

    }
}
