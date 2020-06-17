package com.zhao.mybatis.annotation;


import com.zhao.mybatis.utils.ZhaoImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Import(ZhaoImportBeanDefinitionRegistrar.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZhaoMapperScan {
	String value() default "";
}
