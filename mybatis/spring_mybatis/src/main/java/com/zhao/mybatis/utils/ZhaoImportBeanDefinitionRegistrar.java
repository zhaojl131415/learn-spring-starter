package com.zhao.mybatis.utils;

import com.zhao.mybatis.annotation.ZhaoMapperScan;
import com.zhao.mybatis.factory.ZhaoSessionFactory;
import com.zhao.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 */
public class ZhaoImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {


		Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(ZhaoMapperScan.class.getName());
		Object value = annotationAttributes.get("value");
		System.out.println(value);
		// 获取scan目录

		// 扫描目录下的标有@ZhaoMapper文件

		// 遍历
//		for() {
//			BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
//			BeanDefinition beanDefinition = builder.getBeanDefinition();
//			beanDefinition.setBeanClassName(ZhaoSessionFactory.class.getName());
//			beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserMapper.class);
//
//			registry.registerBeanDefinition("userMapper", beanDefinition);
//		}



		// 能够得到一个实现了UserMapper的代理对象
//		UserMapper userMapper = (UserMapper) ZhaoBatisFactory.getMapper(UserMapper.class);

	}
}
