package com.zhao.listener;

import com.zhao.event.FileUploadEvent;
import com.zhao.event.ZhaoApplicationEvent;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-29 14:50
 */
public class FileUploadListener implements ZhaoApplicationListener<FileUploadEvent> {

    @Override
    public void doEvent(FileUploadEvent event) {
        double i1 = event.getFileSize();
        double d = event.getReadSize()/i1;
//                map.put("文件ID",d*100);
//                map.remove("文件ID")
        System.out.println("当前文件上传进度百分比:"+d*100+"%");

    }
}
