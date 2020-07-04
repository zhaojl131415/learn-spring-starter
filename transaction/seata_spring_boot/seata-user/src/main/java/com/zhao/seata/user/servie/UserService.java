package com.zhao.seata.user.servie;

import com.zhao.seata.user.entity.User;
import com.zhao.seata.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 12:56
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void points(Integer userId, Integer point) {
        log.info("------->User中添加积分开始");
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new NullPointerException();
        } else {
            userRepository.point(userId, user.getPoint() + point);
        }
        log.info("------->User中添加积分结束");
    }
}
