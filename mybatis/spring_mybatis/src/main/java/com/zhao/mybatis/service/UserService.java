package com.zhao.mybatis.service;

import com.zhao.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-05-22 22:11
 */
public interface UserService {



    public List<String> queryUser();
}
