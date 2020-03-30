package com.zhao.classLoader;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;

public class ZhaoListener extends FileAlterationListenerAdaptor {
    @Override
    public void onFileChange(File file) {
        if (file.getName().endsWith(".class")) {
            try {
                // 文件修改：重新实例化ZhaoClassLoader，热部署
                ZhaoClassLoader zhaoClassLoader = new ZhaoClassLoader(ZhaoApplication.rootPath, ZhaoApplication.rootPath + "/com");
                ZhaoApplication.stop();
                // classload
                ZhaoApplication.start0(zhaoClassLoader);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        super.onFileChange(file);
    }
}
