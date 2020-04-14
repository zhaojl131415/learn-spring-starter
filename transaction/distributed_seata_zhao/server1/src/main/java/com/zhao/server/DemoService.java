package com.zhao.server;

import com.zhao.transaction.annotation.ZhaoGlobalTransaction;
import com.zhao.transaction.transactional.ZhaoTransactionManager;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoService {

    @Autowired
    private DemoDao demoDao;

    @ZhaoGlobalTransaction(isStart = true)
    @Transactional
    public void test() {
        demoDao.insert("server1");
        getServer("http://localhost:8082/server2/test");
        getServer("http://localhost:8083/server3/test");
//        int i = 100/0;
    }

    public String getServer(String url) {
        String result = "";
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-type", "application/json");
            httpGet.addHeader("groupId", ZhaoTransactionManager.getCurrentGroupId());
            httpGet.addHeader("transactionCount", String.valueOf(ZhaoTransactionManager.getTransactionCount()));
            CloseableHttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
            response.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
