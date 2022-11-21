package com.zhao;

import com.zhao.service.HelloService;

public class ByteBuddyClient {
    public static void main(String[] args) {
        /**
         * 添加虚拟机运行参数
         * -javaagent:agent-java/bytebuddy/bytebuddy-server/target/bytebuddy-server-1.0-SNAPSHOT.jar
         */
        HelloService helloService = new HelloService();
        helloService.say();
        helloService.say2();
    }
}