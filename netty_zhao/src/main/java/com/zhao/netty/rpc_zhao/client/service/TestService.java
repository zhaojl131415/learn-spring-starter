package com.zhao.netty.rpc_zhao.client.service;

import java.util.List;

public interface TestService {
    List<String> listAll();

    String listByid(Integer id);
}
