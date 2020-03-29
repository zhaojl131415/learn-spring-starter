package com.zhao.listener;

import com.zhao.event.ZhaoApplicationEvent;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-29 14:43
 */
public interface ZhaoApplicationListener<E extends ZhaoApplicationEvent> {

    void doEvent(E e);
}
