package com.zhao.transaction.transactional;

import com.zhao.transaction.util.Task;

/**
 * 子事务
 */
public class ZhaoTransaction {

    // 事务组id
    private String groupId;
    // 当前事务id
    private String transactionId;
    // 事务结果类型
    private TransactionType transactionType;   // commit-待提交，rollback-待回滚
    private Task task = new Task();

    public ZhaoTransaction(String groupId, String transactionId) {
        this.groupId = groupId;
        this.transactionId = transactionId;
        this.task = new Task();
    }

    public ZhaoTransaction(String groupId, String transactionId, TransactionType transactionType) {
        this.groupId = groupId;
        this.transactionId = transactionId;
        this.transactionType = transactionType;
    }

    public Task getTask() {
        return task;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
