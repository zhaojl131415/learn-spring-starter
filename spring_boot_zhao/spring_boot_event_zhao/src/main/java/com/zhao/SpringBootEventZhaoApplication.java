package com.zhao;

import com.zhao.event.AEvent;
import com.zhao.event.BEvent;
import com.zhao.listener.A1Listener;
import com.zhao.listener.AListener;
import com.zhao.listener.BListener;
import com.zhao.listener.FileUploadListener;
import com.zhao.manager.ZhaoEventListenManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;


@SpringBootApplication
public class SpringBootEventZhaoApplication {

    public static void main(String[] args) {
//        SpringApplication.run(SpringBootEventZhaoApplication.class, args);
        ZhaoEventListenManager.addListener(new AListener());
        ZhaoEventListenManager.addListener(new BListener());
        ZhaoEventListenManager.addListener(new A1Listener());

        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("你要发布什么事件:");
            String scan = scanner.next();
            if (scan.equals("A")){
                ZhaoEventListenManager.pushEvent(new AEvent("zhao"));
            }else{
                ZhaoEventListenManager.pushEvent(new BEvent("gong"));
            }
        }
    }

}
