package com.zhao.server;

import com.zhao.transaction.annotation.ZhaoGlobalTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoService {

    @Autowired
    private DemoDao demoDao;


    @ZhaoGlobalTransaction(isEnd = true)
    @Transactional
    public void test() {
        demoDao.insert("server3");
    }
}
