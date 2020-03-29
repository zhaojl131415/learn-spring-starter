package com.spring.listener;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-29 15:41
 */
@Getter
@Setter
public class InstanceRegisterEvent extends ApplicationEvent {

    private String instanceInfo;

    public InstanceRegisterEvent(Object source) {
        super(source);
    }
}
