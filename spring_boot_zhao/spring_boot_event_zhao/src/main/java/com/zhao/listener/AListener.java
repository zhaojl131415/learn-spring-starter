package com.zhao.listener;

import com.zhao.event.AEvent;
import com.zhao.event.FileUploadEvent;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-29 14:50
 */
public class AListener implements ZhaoApplicationListener<AEvent> {

    @Override
    public void doEvent(AEvent aEvent) {
        System.out.println("A监听到了A事件:" + aEvent.getName());
    }
}
