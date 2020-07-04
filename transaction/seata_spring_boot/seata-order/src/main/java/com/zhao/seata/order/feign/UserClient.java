package com.zhao.seata.order.feign;

import com.zhao.seata.domain.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 10:31
 */
@FeignClient(value = "seata-user")
public interface UserClient {

    @PostMapping(value = "/user/points")
    void points(@RequestBody UserVO userVO);
}
