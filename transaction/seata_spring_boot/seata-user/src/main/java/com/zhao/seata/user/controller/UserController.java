package com.zhao.seata.user.controller;

import com.zhao.seata.domain.vo.UserVO;
import com.zhao.seata.user.servie.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 12:55
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/points")
    public void points(@RequestBody @Valid UserVO vo) {
        userService.points(vo.getUserId(), vo.getPoint());
    }
}
