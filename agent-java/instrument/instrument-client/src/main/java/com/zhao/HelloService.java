package com.zhao;

public class HelloService {
    
    
    public String say(){
        System.out.println("===hello world====");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello world";
    }
    
    @ZhaoAgent
    public String say2(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello world";
    }
    
}
