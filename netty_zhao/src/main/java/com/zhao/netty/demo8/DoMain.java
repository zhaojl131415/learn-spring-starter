package com.zhao.netty.demo8;

public class DoMain {

    public static void main(String[] args) {
        WebSocketServerImpl socket = new WebSocketServerImpl("localhost", 9999);
        socket.start();
    }
}