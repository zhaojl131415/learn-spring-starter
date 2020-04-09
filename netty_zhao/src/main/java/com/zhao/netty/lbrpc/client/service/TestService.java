package com.zhao.netty.lbrpc.client.service;

import java.util.List;

public interface TestService {
    List<String> listAll();

    String listByid(Integer id);
}
