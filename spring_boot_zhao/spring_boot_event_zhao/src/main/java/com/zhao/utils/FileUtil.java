package com.zhao.utils;



//假设现在公司让你开发一个文件操作帮助类
//定义一个文件读写方法   读取某个文件 写到某个类里面去

//但是  有可能会有需要记录文件读取进度条的需求

import com.zhao.event.AEvent;
import com.zhao.event.BEvent;
import com.zhao.event.FileUploadEvent;
import com.zhao.listener.A1Listener;
import com.zhao.listener.AListener;
import com.zhao.listener.BListener;
import com.zhao.listener.FileUploadListener;
import com.zhao.manager.ZhaoEventListenManager;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

//有时候调用文件读取不需要进度条
//有时候需要进度条 如何实现？
public class FileUtil {





    public static int READ_SIZE= 100;


    public  static  void fileWrite(InputStream is, OutputStream os) throws Exception{
        fileWrite(is,os,null);
    }

    public static void fileWrite(InputStream is, OutputStream os,FileListener fileListener) throws Exception{
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos  = new BufferedOutputStream(os);
        //文件总大小
        int fileSize = is.available();
        //一共读取了多少
        int readSize = 0;
        byte[] b = new byte[READ_SIZE];
        boolean f = true;
        while (f){
            //文件实在小于第一次读的时候
            if (fileSize<READ_SIZE){
                byte[] bytes = new byte[fileSize];
                bis.read(bytes);
                bos.write(bytes);
                readSize = fileSize;
                f = false;
//                break;
                //当你是最后一次读的时候
            }else if(fileSize<readSize+READ_SIZE){
                byte[] bytes = new byte[fileSize-readSize];
                readSize = fileSize;
                bis.read(bytes);
                bos.write(bytes);
                f = false;
//                break;
            }else{
                bis.read(b);
                readSize +=READ_SIZE;
                bos.write(b);
            }



            if (fileListener!=null){
                fileListener.updateLoad(fileSize,readSize);
            }


            ZhaoEventListenManager.pushEvent(new FileUploadEvent(fileSize,readSize));
        }
        bis.close();
        bos.close();
    }


  static  Map<String,Double>  map ;
    public static void main(String[] args) throws Exception {


        String path = "/Users/zhaojinliang/Documents/";

        File file = new File(path + "写bzp.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        fileWrite(new FileInputStream(new File(path + "读bzp.txt")), new FileOutputStream(file));
        fileWrite(new FileInputStream(new File(path + "读bzp.txt")), new FileOutputStream(file), new FileListener() {
            @Override
            public void updateLoad(int fileSize, int readSize) {
                double i1 = fileSize;
                double d = readSize/i1;
//                map.put("文件ID",d*100);
//                map.remove("文件ID")
                System.out.println("当前文件上传进度百分比:"+d*100+"%");

            }
        });
    }


}
