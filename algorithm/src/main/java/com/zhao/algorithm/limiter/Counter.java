package com.zhao.algorithm.limiter;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/10/9
 */
public class Counter {
    public long timeStamp = System.currentTimeMillis(); // 当前时间
    public int reqCount = 0; // 初始化计数器
    // public final int limit = 100; // 时间窗口内最大请求数
    // public final long interval = 1000 * 60; // 时间窗口ms
    // public boolean limit() {
    // long now = System.currentTimeMillis();
    // if (now < timeStamp + interval) { 13 // 在时间窗口内 14 reqCount++; 15 // 判断当前时间窗口内是否超过最大请求控制数 16 return reqCount <= limit; 17 } else { 18 timeStamp = now; 19 // 超时后重置 20 reqCount = 1; 21 return true; 22 } 23 }
}
