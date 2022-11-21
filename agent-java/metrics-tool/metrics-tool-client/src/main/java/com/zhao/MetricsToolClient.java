package com.zhao;

import java.util.ArrayList;
import java.util.List;

public class MetricsToolClient {
    public static void main(String[] args) {
        /**
         * 添加虚拟机运行参数
         * -javaagent:agent-java/metrics-tool/metrics-tool-server/target/metrics-tool-server-1.0-SNAPSHOT.jar
         */
        boolean flag = true;
        while (flag) {
            List list = new ArrayList<String>();
            list.add("Hello World");
        }
    }
}