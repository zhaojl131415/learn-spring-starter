package com.zhao.transaction.intercept;

import com.zhao.transaction.transactional.ZhaoTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求拦截器
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {

    /**
     * 拦截请求中的事务组id(groupId)和事务数量
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String groupId = request.getHeader("groupId");
        String transactionCount = request.getHeader("transactionCount");
        ZhaoTransactionManager.setCurrentGroupId(groupId);
        ZhaoTransactionManager.setTransactionCount(Integer.valueOf(transactionCount == null ? "0" : transactionCount));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
