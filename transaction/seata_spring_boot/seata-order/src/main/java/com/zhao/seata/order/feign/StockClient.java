package com.zhao.seata.order.feign;

import com.zhao.seata.domain.vo.StockVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 10:32
 */
@FeignClient(value = "seata-stock")
public interface StockClient {

    @PostMapping(value = "/stock/deduct")
    void deduct(@RequestBody StockVO stockVO);

}
