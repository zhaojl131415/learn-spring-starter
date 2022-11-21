package com.zhao;

public class AgentClient {
    public static void main(String[] args) {
        /**
         * 添加虚拟机运行参数
         * -javaagent:agent-java/agent/agent-server/target/agent-server-1.0-SNAPSHOT.jar=zhao
         */
        System.out.println("Hello world!");
    }
}