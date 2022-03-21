package com.zhao.server;

//import com.zhao.transaction.annotation.ZhaoGlobalTransaction;
//import com.zhao.transaction.util.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoService {

    @Autowired
    private DemoDao demoDao;

//    @ZhaoGlobalTransaction(isStart = true)
    @Transactional
    public void test() {
        demoDao.insert("server1");
        getServer("http://localhost:8082/server2/test");
        getServer("http://localhost:8083/server3/test");
//        int i = 100/0;
    }

    public String getServer(String url) {
        // 远程调用: 在header里添加事务组id(groupId)和事务数量
//        return HttpClient.get(url);
        return null;
    }

}
