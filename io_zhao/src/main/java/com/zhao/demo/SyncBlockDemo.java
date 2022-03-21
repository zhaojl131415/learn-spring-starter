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

        // 异步阻塞: 回调
        int asyncResult = asyncMethod((a,b) -> {
            int c = a + b;
            return c;
        },1, 2);
        System.out.println("基于异步阻塞1:"+asyncResult);

        // 异步阻塞: 回调
        Async2 async = new Async2();
        asyncMethod2(async, 1, 2);
        System.out.println("基于异步阻塞2:"+async.getReturnValue());

        // 异步非阻塞
        Async2 async2 = new Async2();
        new Thread(() -> asyncMethod2(async, 1, 2)).start();
        System.out.println("基于异步非阻塞:"+async2.getReturnValue());


    }

    public static int syncMethod(int a, int b){
        int c = a + b;
        return c;
    }

    public static int asyncMethod(AsyncInterface async, int a, int b){
        return async.callback(a,b);
    }

    public static void asyncMethod2(Async2 async, int a, int b){
        async.setReturnValue(a+b);
        async.setFlag(true);
    }


    interface AsyncInterface {
       int callback(int a, int b);
    }



    static class Async implements AsyncInterface {
        @Override
        public int callback(int a, int b) {
            return a + b;
        }
    }

    static class Async2 {

        private Object returnValue;
        private Boolean flag;

        public void setReturnValue(Object returnValue) {
            this.returnValue = returnValue;
        }

        public Boolean getFlag() {
            return flag;
        }

        public void setFlag(Boolean flag) {
            this.flag = flag;
        }

        public Object getReturnValue() {
            return flag ? returnValue : null;
        }
    }
}
