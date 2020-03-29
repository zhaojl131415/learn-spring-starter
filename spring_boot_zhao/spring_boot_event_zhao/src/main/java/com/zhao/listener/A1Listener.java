package com.zhao.listener;

import com.zhao.event.AEvent;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-29 14:50
 */
public class A1Listener implements ZhaoApplicationListener<AEvent> {

    @Override
    public void doEvent(AEvent aEvent) {
        System.out.println("A1监听到了A事件:" + aEvent.getName());
    }
}
