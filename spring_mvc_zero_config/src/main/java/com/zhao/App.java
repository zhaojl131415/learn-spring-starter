package com.zhao;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.startup.Tomcat;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-22 16:52
 */
public class App {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8081);
        tomcat.getConnector();
        //
        System.out.println(App.class.getResource("/"));
        // 只会去初始化一个context的资源目录 并不会加载web的生命周期
        Context context = tomcat.addContext("/", App.class.getResource("/").getPath().replaceAll("%20"," "));
//         会加载web的生命周期
//        Context context = tomcat.addWebapp("/", App.class.getResource("/").getPath().replaceAll("%20", " "));
        /**
         * web的生命周期:包括servlet的加载,监听器的加载,过滤器的加载,onStartup方法的调用等等一系列的操作
         */


        // webapps
        // .war   文件夹
//        context.adds
//        Tomcat.addServlet(context,"default",new DefaultServlet());
//        context.addServletMappingDecoded("/*","default");
        // 添加生命周期监听器, 会加载web的生命周期
        context.addLifecycleListener((LifecycleListener) Class.forName(tomcat.getHost().getConfigClass()).newInstance());
        tomcat.start();
//        System.out.println(tomcat.getHost());
//        System.out.println(tomcat.getServer());
//        System.out.println(tomcat.getConnector());

        // 阻塞. 不然main方法结束
        tomcat.getServer().await();
    }
}
