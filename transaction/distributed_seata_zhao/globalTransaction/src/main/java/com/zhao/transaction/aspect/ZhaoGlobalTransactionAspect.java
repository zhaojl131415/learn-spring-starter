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

    @Around("@annotation(com.zhao.transaction.annotation.ZhaoGlobalTransaction)")
    public void invoke(ProceedingJoinPoint point) {
        // 打印出这个注解所对应的方法
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        ZhaoGlobalTransaction annotation = method.getAnnotation(ZhaoGlobalTransaction.class);

        String groupId = "";
        if (annotation.isStart()) {
            groupId = ZhaoTransactionManager.createTransactionGroup();
        } else {
            groupId = ZhaoTransactionManager.getCurrentGroupId();
        }

        ZhaoTransaction zhaoTransaction = ZhaoTransactionManager.createTransaction(groupId);

        try {
            // spring会开启mysql事务
            point.proceed();
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
