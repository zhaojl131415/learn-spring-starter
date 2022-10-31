package com.zhao.guava;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Parameter;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/9/9
 */
public class InvokableDemo {
    public static void main(String[] args) {
        // 对象
        InvokableDemo object = new InvokableDemo();
        // 获去对象对应的类
        Class clazz = object.getClass();
        Method[] invokableSourceList = clazz.getMethods();
//        Constructor[] invokableSourceList =  clazz.getConstructors();
        if (invokableSourceList.length > 0) {
            for (Method item : invokableSourceList) {
                System.out.println("========================================");
                // 方法名字
                System.out.println("方法名字：" + item.getName());
                // 把Method包装成Invokable
                Invokable methodInvokable = Invokable.from(item);
                // getDeclaringClass() 获取定义该方法的类
                System.out.println("定义该方法的类：" + methodInvokable.getDeclaringClass());
                // getOwnerType() 获取定义该方法的class的包装对象Type
                System.out.println("定义该方法的类：" + methodInvokable.getOwnerType().getType());
                // isOverridable() 该方法是否可以重写
                System.out.println("是否可以重写：" + methodInvokable.isOverridable());
                // isVarArgs() 该方法是否可变参数
                System.out.println("是否可变参数：" + methodInvokable.isVarArgs());
                // getReturnType() 该方法返回值类型
                System.out.println("返回值类型：" + methodInvokable.getReturnType().getType());
                // getParameters() 获取参数
                ImmutableList<Parameter> parameterList = methodInvokable.getParameters();
                for (int index = 0; index < parameterList.size(); index++) {
                    System.out.println("方法参数" + index + ": " + parameterList.get(index).getType());
                }
                // getExceptionTypes() 获取异常类
                ImmutableList<TypeToken> exceptionList = methodInvokable.getExceptionTypes();
                for (int index = 0; index < exceptionList.size(); index++) {
                    System.out.println("异常类" + index + ": " + exceptionList.get(index).getType());
                }

                // getAnnotatedReturnType()
//                AnnotatedType annotatedType = methodInvokable.getAnnotatedReturnType();
//                System.out.println("annotatedType: " + annotatedType.getType());
            }
        }
    }
}
