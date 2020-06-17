package com.zhao.spring.boot.autoconfigure.config;

import com.zhao.spring.boot.autoconfigure.entity.Permission;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class PermissionImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Permission.class);
        registry.registerBeanDefinition("permission", rootBeanDefinition);      // 注册一个名字叫permission的bean定义
    }
}
