//package com.zhao.servlet.handler;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.GenericApplicationContext;
//import org.springframework.context.support.StaticMessageSource;
//import org.springframework.lang.Nullable;
//
///**
// * @author zhaojinliang
// * @version 1.0
// * @description TODO
// * @date 2020-03-24 15:44
// */
//public class ZhaoApplicationContext extends GenericApplicationContext {
//
//    //判断spring 容器有没有执行过refresh这个方法
//    //但是我们测试不需要判断 所以直接覆写他的逻辑 不让他报错
//    @Override
//    public  void assertBeanFactoryActive(){
//
//    }
//
//    public ZhaoApplicationContext() throws BeansException {
//        this((ApplicationContext)null);
//    }
//
//    public ZhaoApplicationContext(@Nullable ApplicationContext parent) throws BeansException {
//        super(parent);
//    }
//
//    public void registerSingleton(String name, Class<?> clazz) throws BeansException {
//        GenericBeanDefinition bd = new GenericBeanDefinition();
//        bd.setBeanClass(clazz);
//        this.getDefaultListableBeanFactory().registerBeanDefinition(name, bd);
//    }
//}
