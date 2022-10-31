package com.spring.cloud.alibaba.sentinel.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.spring.cloud.alibaba.sentinel.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/9/26
 */
@Service
@Slf4j
public class HelloServiceImpl implements HelloService {

    @SentinelResource(value = "userInfo", blockHandler = "blockHandlerMethod")
    @Override
    public String getUserInfo() {
        return "zhao";
    }

    public String blockHandlerMethod(BlockException e) {
        log.info(e.getMessage());
        return "限流异常.";
    }
}
