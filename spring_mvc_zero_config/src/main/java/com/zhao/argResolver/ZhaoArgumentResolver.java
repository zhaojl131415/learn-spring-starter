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

        //通过Reques.getParameter();
        //request.getSession

        //从redis 里面获取....

        //父子容器
        //参数类型解析器
        //
        System.out.println(1);
        HashMap hashMap = new HashMap();
        hashMap.put("name","1");
        return "abc";
    }
}
