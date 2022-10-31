package com.zhao.guava;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/9/9
 */
public class RangeDemo {
    public static void main(String[] args) {
//        rangeDefinition();
        rangeMapTest();
    }

    private static void rangeDefinition(){
        Range<Integer> a1 = Range.closed(0,1);
        System.out.println(a1);
        Range<Integer> a2 = Range.open(0,1);
        System.out.println(a2);
        Range<Integer> a3 = Range.openClosed(0,1);
        System.out.println(a3);
        Range<Integer> a4 = Range.closedOpen(0,1);
        System.out.println(a4);
        Range<Integer> a5 = Range.atLeast(0);
        System.out.println(a5);
        Range<Integer> a6 = Range.greaterThan(0);
        System.out.println(a6);
        Range<Integer> a7 = Range.atMost(0);
        System.out.println(a7);
        Range<Integer> a8 = Range.lessThan(0);
        System.out.println(a8);
    }

    private static void rangeMapTest(){
        RangeMap<Integer, String> level = TreeRangeMap.create();
        level.put(Range.closed(90,100), "A");
        level.put(Range.closedOpen(80,90), "B");
        level.put(Range.lessThan(80), "C");

        System.out.println(level.get(90));
        System.out.println(level.get(80));
        System.out.println(level.get(75));
    }
}
