package com.zhao.netty.demo8;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;

public class DoMain {

    public static void main(String[] args) {
//        WebSocketServerImpl socket = new WebSocketServerImpl("localhost", 9999);
//        socket.start();

        String inTime = "2020-09-10 20:12", outTime = "2020-09-10 20:15";
        DateTime startTime = DateTime.parse(inTime, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
        DateTime endTime = DateTime.parse(outTime, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
        System.out.println(Minutes.minutesBetween(startTime, endTime).getMinutes());
    }
}