package com.zhao;

import java.lang.instrument.Instrumentation;

public class AgentServer {


    public static void premain(String args, Instrumentation instrumentation){
        System.out.println("premain：" + args);
    }
}