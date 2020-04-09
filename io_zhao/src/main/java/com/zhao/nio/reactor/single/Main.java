package com.zhao.nio.reactor.single;

import java.io.IOException;

/**
 * 单线程: 全都是直接调用run方法, 没有新建线程, 各个事件会阻塞执行
 *
 * @author zhaojinliang
 * @date 2020-04-09 09:57
 */
public class Main {


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            TCPReactor reactor = new TCPReactor(1333);
            // 这里是直接调用run方法, 没有新建线程调用start(), 只是一个普通的方法调用
            reactor.run();
//            new Thread(reactor).start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}