package com.zhao.argResolver;

import com.zhao.annotation.ZhaoArg;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description 自定义参数解析器
 * @date 2020-03-23 22:03
 */
@Component
public class ZhaoArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ZhaoArg.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 通过webRequest.getSession()或webRequest.getParameter("userId")
        // 从request中获取session/userId, 从 mysql/redis 里面查询获取token或其他信息, 赋值给某个对象返回回去
        System.out.println("ZhaoArgumentResolver");
        HashMap hashMap = new HashMap();
        hashMap.put("name","1");
        return hashMap;
    }
}
