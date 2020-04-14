package com.zhao.transaction.aspect;

import com.zhao.transaction.annotation.ZhaoGlobalTransaction;
import com.zhao.transaction.transactional.ZhaoTransaction;
import com.zhao.transaction.transactional.ZhaoTransactionManager;
import com.zhao.transaction.transactional.TransactionType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 全局事务 切面
 *
 * @author zhaojinliang
 * @date 2020-04-14 15:26
 */
@Aspect
@Component
public class ZhaoGlobalTransactionAspect implements Ordered {

    // ZhaoGlobalTransaction注解切面通知
    @Around("@annotation(com.zhao.transaction.annotation.ZhaoGlobalTransaction)")
    public void invoke(ProceedingJoinPoint point) {
        // 打印出这个注解所对应的方法(切点)
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        ZhaoGlobalTransaction annotation = method.getAnnotation(ZhaoGlobalTransaction.class);

        // 获取注解的信息
        String groupId = "";
        // 判断当前注解表示的方法:是否为第一个子事务
        if (annotation.isStart()) {
            // 如果是第一个子事务, 创建事务组, 并获取事务组id
            groupId = ZhaoTransactionManager.createTransactionGroup();
        } else {
            // 如果不是第一个, 获取当前事务组id
            groupId = ZhaoTransactionManager.getCurrentGroupId();
        }

        // 根据事务组id创建一个子事务
        ZhaoTransaction zhaoTransaction = ZhaoTransactionManager.createTransaction(groupId);

        try {
            // spring会开启mysql事务
            point.proceed();
            // 业务代码执行完后, 添加事务到事务组, 向事务组告知当前子事务的执行结果情况
            ZhaoTransactionManager.addTransaction(zhaoTransaction, annotation.isEnd(), TransactionType.commit);
        } catch (Exception e) {
            ZhaoTransactionManager.addTransaction(zhaoTransaction, annotation.isEnd(), TransactionType.rollback);
            e.printStackTrace();
        } catch (Throwable throwable) {
            ZhaoTransactionManager.addTransaction(zhaoTransaction, annotation.isEnd(), TransactionType.rollback);
            throwable.printStackTrace();
        }
    }


    /**
     * 切面的优先级
     * @return
     */
    @Override
    public int getOrder() {
        return 10000;
    }
}
