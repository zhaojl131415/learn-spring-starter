package com.zhao.seata.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 10:32
 */
//@FeignClient(value = "seata-commodity")
public interface CommodityClient {


    @GetMapping(value = "/hello/{message}")
    String hello(@PathVariable("message") String message);

}
