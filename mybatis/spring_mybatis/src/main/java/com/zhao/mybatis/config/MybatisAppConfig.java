package com.zhao.mybatis.config;

import com.zhao.mybatis.annotation.ZhaoMapperScan;
import com.zhao.mybatis.utils.ZhaoImportBeanDefinitionRegistrar;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-15 20:30
 */
@Configuration
@ComponentScan("com.zhao.mybatis")
@ZhaoMapperScan("com.zhao.mybatis")
@MapperScan()
public class MybatisAppConfig {
}
