package com.zhao.listener;

import com.zhao.event.BEvent;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-29 14:50
 */
public class BListener implements ZhaoApplicationListener<BEvent> {

    @Override
    public void doEvent(BEvent bEvent) {
        System.out.println("B监听到了B事件:" + bEvent.getName());
    }
}
