package com.zhao.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zhao.converter.StringConvertDate;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.HttpMessageConverter;

@Configuration
public class AppConfig {

    @Bean
    public TomcatServletWebServerFactory getTomcatServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setPort(80);
        return factory;
    }



//    @Bean
//    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){
//        return new FastJsonHttpMessageConverter();
//    }
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){
        return new FastJsonHttpMessageConverter();
    }

    @Bean
    public Converter converter(){
        return new StringConvertDate("yyyy-MM-dd HH:mm:ss");
    }
}
