package com.zhao.transaction.aspect;

import com.zhao.transaction.annotation.ZhaoTransactional;
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

@Aspect
@Component
public class ZhaoTransactionAspect implements Ordered {


    @Around("@annotation(com.zhao.server.transaction.annotation.Lbtransactional)")
    public void invoke(ProceedingJoinPoint point) {
        // 打印出这个注解所对应的方法
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        ZhaoTransactional lbAnnotation = method.getAnnotation(ZhaoTransactional.class);

        String groupId = "";
        if (lbAnnotation.isStart()) {
            groupId = ZhaoTransactionManager.createLbTransactionGroup();
        } else {
            groupId = ZhaoTransactionManager.getCurrentGroupId();
        }

        ZhaoTransaction zhaoTransaction = ZhaoTransactionManager.createLbTransaction(groupId);

        try {
            // spring会开启mysql事务
            point.proceed();
            ZhaoTransactionManager.addLbTransaction(zhaoTransaction, lbAnnotation.isEnd(), TransactionType.commit);
        } catch (Exception e) {
            ZhaoTransactionManager.addLbTransaction(zhaoTransaction, lbAnnotation.isEnd(), TransactionType.rollback);
            e.printStackTrace();
        } catch (Throwable throwable) {
            ZhaoTransactionManager.addLbTransaction(zhaoTransaction, lbAnnotation.isEnd(), TransactionType.rollback);
            throwable.printStackTrace();
        }
    }


    @Override
    public int getOrder() {
        return 10000;
    }
}
