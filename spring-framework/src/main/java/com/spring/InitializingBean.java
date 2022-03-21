package com.spring;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2021-08-09 17:16
 */
public interface InitializingBean {

    void afterPropertiesSet() throws Exception;
}
