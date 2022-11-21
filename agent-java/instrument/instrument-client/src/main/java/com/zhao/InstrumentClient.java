package com.zhao;

public class InstrumentClient {
    public static void main(String[] args) {
        /**
         * 添加虚拟机运行参数
         * -javaagent:agent-java/instrument/instrument-server/target/instrument-server-1.0-SNAPSHOT.jar
         */
        HelloService helloService = new HelloService();
        helloService.say();
        helloService.say2();
    }
}