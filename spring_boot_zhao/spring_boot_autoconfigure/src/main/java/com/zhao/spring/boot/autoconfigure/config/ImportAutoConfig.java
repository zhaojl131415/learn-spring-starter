package com.zhao.spring.boot.autoconfigure.config;

import com.zhao.spring.boot.autoconfigure.entity.User;
import org.springframework.context.annotation.Import;

/**
 * spring4.2之前,@Import只支持导入配置类(@Configuration修饰的类、ImportSelector实现类和ImportBeanDefinitionRegistrar实现类)，
 * 而spring4.2及之后不仅支持导入配置类，同时也支持导入常规的java类(如普通的User类)
 *
 * @author zhaojinliang
 * @date 2020-06-13 14:57
 */
@Import({
        AnimalConfig.class, // @Configuration修饰的类
        RoleImportSelector.class, // ImportSelector实现类
        PermissionImportBeanDefinitionRegistrar.class, // ImportBeanDefinitionRegistrar实现类
        User.class // 常规的java类
})
//@ZhaoEnableAutoConfiguration
public class ImportAutoConfig {
}
