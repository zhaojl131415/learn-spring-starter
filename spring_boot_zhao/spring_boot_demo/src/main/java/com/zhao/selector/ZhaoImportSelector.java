package com.zhao.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class ZhaoImportSelector implements ImportSelector {
    /**
     * 返回一个字符串数组，里面包含了需要被spring容器初始化的类的全限定名
     * @param annotationMetadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.zhao.config.AppConfig"};
    }
}
