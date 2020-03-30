package com.zhao.classLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ZhaoClassLoader extends ClassLoader {
    // 目的：让缓存里永远能返回一个class对象，这样就不会走父类加载器

    // 项目根路径
    private String rootPath;

    // 用于存储所有需要有当前类加载器加载的类
    private List<String> clazzs;


    // 打破双亲委派： 在构造方法里加载class    loadClass
    // classPaths 需要被热部署加载器去加载的目录
    public ZhaoClassLoader( String rootPath, String... classPaths) throws Exception {
        this.rootPath = rootPath;
        this.clazzs = new ArrayList<>();

        for (String classPath : classPaths) {
            scan(new File(classPath));
        }
    }

    // 扫描
    public void scan(File file) throws Exception {
        if (file.isDirectory()){
            for (File f : file.listFiles()) {
                scan(f);
            }
        }else{
            String fileName = file.getName();
            String filePath = file.getPath();
            String  endName = fileName.substring(fileName.lastIndexOf(".")+1);
            if (endName.equals("class")){
                //现在我们加载到的是一个Class文件
                //如何吧一个Class文件 加载成一个Class对象？？？？
                InputStream inputStream = new FileInputStream(file);
                byte[] bytes = new byte[(int) file.length()];
                inputStream.read(bytes);

                //文件名转类名
                String className = fileNameToClassName(filePath);

                defineClass(className, bytes, 0, bytes.length);
                clazzs.add(className);
                //loadClass 是从当前ClassLoader里面去获取一个Class对象
            }
        }
    }

    // 文件名转换成class名  com.zhao.User
    public String fileNameToClassName(String filePath) {
        String classPath = filePath.replace(rootPath+File.separator, "");
        // 替换/, 得到: com.zhao.User.class
        classPath = classPath.replaceAll("\\\\", ".");
        // 得到: com.zhao.User
        return classPath.substring(0, classPath.lastIndexOf("."));
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(name);
        if (clazz == null) {
            if(!clazzs.contains(name)) {
                clazz = getSystemClassLoader().loadClass(name);
            } else {
                throw new ClassNotFoundException("没有加载到类");
            }
        }
        return clazz;
    }


    public static void main(String[] args) throws Exception {
        ZhaoApplication.run(ZhaoClassLoader.class);


//        // 先做热替换，先加载单个class
//        String rootPath = ZhaoClassLoader.class.getResource("/").getPath().replaceAll("%20", "");
//        rootPath = new File(rootPath).getPath();
//        while (true) {
//
//            ZhaoClassLoader zhaoClassLoader = new ZhaoClassLoader(rootPath, rootPath + File.separator + "com");
//            Class<?> aClass = zhaoClassLoader.loadClass("com.zhao.classLoader.Test");
//            aClass.getMethod("test").invoke(aClass.newInstance());
//            new Test().test();
//
//            Thread.sleep(2000);
//
//        }
    }


    // 两个ClassLoader 一个负责加载需要被热部署的class，一个加载系统的class
}
