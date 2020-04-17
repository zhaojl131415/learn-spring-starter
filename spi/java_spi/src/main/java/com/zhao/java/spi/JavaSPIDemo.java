package com.zhao.java.spi;

import com.zhao.java.spi.service.DogService;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 使用步骤：
 * 1、服务调用方通过ServiceLoader.load加载服务接口的实现类实例；
 * 2、服务提供方实现服务接口后，在自己Jar包的META-INF/services目录下新建一个接口名全名的文件，并将具体实现类全名写入。
 *
 * 缺点：
 * Java SPI 如果有多个实现类，不能单独获取某个指定的实现类
 * Java SPI 没有IOC和AOP机制，对于实现类的属性无法依赖注入，也无法实现方法上的切面
 */
public class JavaSPIDemo {
    public static void main(String[] args) {
        ServiceLoader<DogService> load = ServiceLoader.load(DogService.class);
        Iterator<DogService> iterator = load.iterator();
        while (iterator.hasNext()) {
            DogService next = iterator.next();
            next.getColor();
        }
    }
}
