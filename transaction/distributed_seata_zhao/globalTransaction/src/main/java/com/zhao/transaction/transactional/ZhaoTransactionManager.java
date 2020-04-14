package com.zhao.transaction.transactional;

import com.alibaba.fastjson.JSONObject;
import com.zhao.transaction.netty.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ZhaoTransactionManager {


    private static NettyClient nettyClient;

    private static ThreadLocal<ZhaoTransaction> current = new ThreadLocal<>();
    private static ThreadLocal<String> currentGroupId = new ThreadLocal<>();
    private static ThreadLocal<Integer> transactionCount = new ThreadLocal<>();

    @Autowired
    public void setNettyClient(NettyClient nettyClient) {
        ZhaoTransactionManager.nettyClient = nettyClient;
    }

    public static Map<String, ZhaoTransaction> LB_TRANSACION_MAP = new HashMap<>();

    /**
     * 创建事务组，并且返回groupId
     * @return
     */
    public static String createLbTransactionGroup() {
        String groupId = UUID.randomUUID().toString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupId", groupId);
        jsonObject.put("command", "create");
        nettyClient.send(jsonObject);
        System.out.println("创建事务组");

        currentGroupId.set(groupId);
        return groupId;
    }

    /**
     * 创建分布式事务
     * @param groupId
     * @return
     */
    public static ZhaoTransaction createLbTransaction(String groupId) {
        String transactionId = UUID.randomUUID().toString();
        ZhaoTransaction zhaoTransaction = new ZhaoTransaction(groupId, transactionId);
        LB_TRANSACION_MAP.put(groupId, zhaoTransaction);
        current.set(zhaoTransaction);
        addTransactionCount();

        System.out.println("创建事务");

        return zhaoTransaction;
    }

    /**
     * 添加事务到事务组
     * @param zhaoTransaction
     * @param isEnd
     * @param transactionType
     * @return
     */
    public static ZhaoTransaction addLbTransaction(ZhaoTransaction zhaoTransaction, Boolean isEnd, TransactionType transactionType) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupId", zhaoTransaction.getGroupId());
        jsonObject.put("transactionId", zhaoTransaction.getTransactionId());
        jsonObject.put("transactionType", transactionType);
        jsonObject.put("command", "add");
        jsonObject.put("isEnd", isEnd);
        jsonObject.put("transactionCount", ZhaoTransactionManager.getTransactionCount());
        nettyClient.send(jsonObject);
        System.out.println("添加事务");
        return zhaoTransaction;
    }

    public static ZhaoTransaction getLbTransaction(String groupId) {
        return LB_TRANSACION_MAP.get(groupId);
    }

    public static ZhaoTransaction getCurrent() {
        return current.get();
    }
    public static String getCurrentGroupId() {
        return currentGroupId.get();
    }

    public static void setCurrentGroupId(String groupId) {
        currentGroupId.set(groupId);
    }

    public static Integer getTransactionCount() {
        return transactionCount.get();
    }

    public static void setTransactionCount(int i) {
        transactionCount.set(i);
    }

    public static Integer addTransactionCount() {
        int i = (transactionCount.get() == null ? 0 : transactionCount.get()) + 1;
        transactionCount.set(i);
        return i;
    }
}
