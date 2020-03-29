package com.zhao.manager;

import com.zhao.event.ZhaoApplicationEvent;
import com.zhao.listener.ZhaoApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-29 14:44
 */
public class ZhaoEventListenManager {


    //保存所有的监听器
    static List<ZhaoApplicationListener<?>> list = new ArrayList<>();


    //添加监听器  //如果要做得优雅一点 可以考虑扫描项目
    //定义注解
    public static void addListener(ZhaoApplicationListener listener) {
        list.add(listener);
    }

    //????
    //判断一下有哪些人对这个时间感兴趣
    public static void pushEvent(ZhaoApplicationEvent event) {
        for (ZhaoApplicationListener listener : list) {
            //拿泛型
            Class clazz = (Class) ((ParameterizedType) listener.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
            //判断一下泛型
//            tClass.isAssignableFrom()
            if (clazz.equals(event.getClass())) {
                listener.doEvent(event);
            }
        }
    }

}
