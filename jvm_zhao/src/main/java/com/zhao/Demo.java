package com.zhao;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-07-13 18:24
 */
public class Demo {

    public static void main(String[] args) {
        int i = 1;
        i = i++;
        System.out.println("i="+i);
        int j = i++;
        System.out.println("i="+i);
        int k = i + ++i * i++;
//        int k = 2 + 3 * 3;
        System.out.println("i="+i);
        System.out.println("j="+j);
        System.out.println("k="+k);
//        4 2 23
//        3+ (4*5)
    }
}
