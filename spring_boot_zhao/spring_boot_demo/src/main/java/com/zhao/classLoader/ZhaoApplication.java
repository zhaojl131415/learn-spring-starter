package com.zhao.classLoader;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public class ZhaoApplication {

    public static String rootPath;

    public static void run(Class<?> clazz) throws Exception {
        String rootPath = ZhaoClassLoader.class.getResource("/").getPath().replaceAll("%20", "");
        rootPath = new File(rootPath).getPath();
        ZhaoApplication.rootPath = rootPath;
        ZhaoClassLoader zhaoClassLoader = new ZhaoClassLoader(rootPath, rootPath + File.separator + "com");
        startListener();
        start0(zhaoClassLoader);

    }

    // 开启一个线程去定时扫描某个文件夹下所有文件，判断当文件有改动时调用监听器
    //要实现文件监听：  写一个线程 去定时监听某个路径下所有的文件
    //如果文件发生改动 就回调监听器
    public static void startListener() throws Exception {
        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(rootPath);
        fileAlterationObserver.addListener(new ZhaoListener());
        FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(500);
        fileAlterationMonitor.addObserver(fileAlterationObserver);
        fileAlterationMonitor.start ();
    }

    // 文件修改后，重新实例化了ClassLoad，和之前类加载器不再是同一个对象，通过这个类加载器加载的class对象也不再是之前的class对象
    public static void start0(ZhaoClassLoader classLoader) throws Exception {
        Class<?> aClass = classLoader.loadClass("com.zhao.classLoader.ZhaoApplication");
        // 反射调用start方法
        aClass.getMethod("start").invoke(aClass.newInstance());

        Thread.sleep(2000);
    }


    public static void start(){
        System.out.println("启动应用程序");
        //Tomcat tomcat = new Tomcat();

        //Controller ...xxxx

        new Test().test();
    }

    public static void stop() {
        // 清理缓存, 清除对象引用
//        ApplicationContext.stop;
        //告诉jvm需要gc了
        System.gc();
        // 运行任何对象的终止方法。
        System.runFinalization();
        System.out.println("退出应用程序");
    }
}
