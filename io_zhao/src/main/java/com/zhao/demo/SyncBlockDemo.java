package com.zhao.demo;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-04 10:22
 *
 *
 * 同步   正常调用
 * 异步   基于回调,一般跟非阻塞一起表示,
 * 阻塞   没开新线程
 * 非阻塞  开了新线程
 */
public class SyncBlockDemo {

    public static void main(String[] args) {

        // 同步
        int syncResult = syncMethod(1, 2);
        System.out.println("基于同步阻塞:"+syncResult);

        // 异步: 回调
        int asyncResult = asyncMethod((a,b) -> {
            int c = a + b;
            return c;
        },1, 2);
        System.out.println("基于异步阻塞:"+asyncResult);


    }

    public static int syncMethod(int a, int b){
        int c = a + b;
        return c;
    }

    public static int asyncMethod(AsyncInterface async, int a, int b){
        return async.callback(a,b);
    }

    public static int asyncMethod2(AsyncInterface async, int a, int b){
        return async.callback(a,b);
    }


    interface AsyncInterface {
       int callback(int a, int b);
    }


    static class Async {

        private Object returnValue;
        private Boolean flag;

        public Object getReturnValue() {
            return flag ? returnValue : null;
        }
    }
}
