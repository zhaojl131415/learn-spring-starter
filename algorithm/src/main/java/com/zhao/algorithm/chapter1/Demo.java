package com.zhao.algorithm.chapter1;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-08-29 14:17
 */
public class Demo {


    public static void main(String[] args) throws Exception {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
// 每个线程拥有前一个线程的引用，需要等待前一个线程终止，才能从等待中返回
            Thread thread = new Thread(new Domino(previous), String.valueOf(i));
            thread.start();
            previous = thread;
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName() + " terminate.");
    }

    static class Domino implements Runnable {
        private Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName() + " terminate111.");
        }
    }


//    public static void main(String[] args) throws Exception {
//        Runner one = new Runner();
//        Thread countThread = new Thread(one, "CountThread1");
//        countThread.start();
//// 睡眠1秒，main线程对CountThread进行中断，使CountThread能够感知中断而结束 TimeUnit.SECONDS.sleep(1);
//        countThread.interrupt();
//        Runner two = new Runner();
//        countThread = new Thread(two, "CountThread2");
//        countThread.start();
//// 睡眠1秒，main线程对Runner two进行取消，使CountThread能够感知on为false而结束 TimeUnit.SECONDS.sleep(1);
//        two.cancel();
//    }
//
//    private static class Runner implements Runnable {
//        private long i;
//        private volatile boolean on = true;
//
//        @Override
//        public void run() {
//            while (on && !Thread.currentThread().isInterrupted()) {
//                i++;
//            }
//            System.out.println(Thread.currentThread().getName() + ": Count i = " + i);
//        }
//
//        public void cancel() {
//            on = false;
//        }
//    }


//    public static void main(String[] args) throws Exception {
//        DateFormat format = new SimpleDateFormat("HH:mm:ss");
//        Thread printThread = new Thread(new Runner(), "PrintThread");
//        printThread.setDaemon(true);
//        printThread.start();
//        TimeUnit.SECONDS.sleep(3);
//// 将PrintThread进行暂停，输出内容工作停止
//        printThread.suspend();
//        System.out.println("main suspend PrintThread at " + format.format(new Date()));
//        TimeUnit.SECONDS.sleep(3);
//// 将PrintThread进行恢复，输出内容继续
//        printThread.resume();
//        System.out.println("main resume PrintThread at " + format.format(new Date()));
//        TimeUnit.SECONDS.sleep(3);
//// 将PrintThread进行终止，输出内容停止
//        printThread.stop();
//        System.out.println("main stop PrintThread at " + format.format(new Date()));
//        TimeUnit.SECONDS.sleep(3);
//    }
//
//    static class Runner implements Runnable {
//        @Override
//        public void run() {
//            DateFormat format = new SimpleDateFormat("HH:mm:ss"); while (true) {
//                System.out.println(Thread.currentThread().getName() + " Run at " + format.format(new Date()));
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


//    public static void main(String[] args) throws Exception {
//// sleepThread不停的尝试睡眠
//        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
//        sleepThread.setDaemon(true);
//// busyThread不停的运行
//        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
//        busyThread.setDaemon(true);
//        sleepThread.start();
//        busyThread.start();
//// 休眠5秒，让sleepThread和busyThread充分运行
//        TimeUnit.SECONDS.sleep(5);
//        sleepThread.interrupt();
//        busyThread.interrupt();
//        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
//        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted()); // 防止sleepThread和busyThread立刻退出
//        TimeUnit.SECONDS.sleep(2);
//    }
//
//    static class SleepRunner implements Runnable {
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    TimeUnit.SECONDS.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    static class BusyRunner implements Runnable {
//        @Override
//        public void run() {
//            while (true) {
//            }
//        }
//    }
}
