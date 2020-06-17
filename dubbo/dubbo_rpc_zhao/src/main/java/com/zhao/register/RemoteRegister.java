package com.zhao.register;

import com.zhao.framework.URL;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.*;

/**
 * 注册中心
 * @author zhao
 */
public class RemoteRegister {

    // <服务名：List(url)>
    private static Map<String, List<URL>> REGISTER = new HashMap<>();

    // 服务注册
    public static void register(String interfaceName, URL url) {
        List<URL> urls = Collections.singletonList(url);
        REGISTER.put(interfaceName, urls);
        saveFile();
    }

    // 服务获取
    public static List<URL> get(String interfaceName){
//        return REGISTER.get(interfaceName);
        return getFile().get(interfaceName);
    }

    // 负载均衡：随机算法
    public static URL getRandom(String interfaceName){
        List<URL> urls = RemoteRegister.get(interfaceName);
        int i = new Random().nextInt(urls.size());
        return urls.get(i);
    }

    // 服务注册写入文件
    private static void saveFile(){
        try {
            FileOutputStream fos = new FileOutputStream("/Users/zhaojinliang/temp.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(REGISTER);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从文件中读取注册的服务
    private static Map<String, List<URL>> getFile(){
        try {
            FileInputStream fis = new FileInputStream("/Users/zhaojinliang/temp.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Map<String, List<URL>> map = (Map<String, List<URL>>) ois.readObject();
            ois.close();
            fis.close();
            return map;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
