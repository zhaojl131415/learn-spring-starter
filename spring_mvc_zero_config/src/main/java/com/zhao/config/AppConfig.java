package com.zhao.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zhao.argResolver.ZhaoArgumentResolver;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-22 16:58
 */
@Configuration
@ComponentScan("com.zhao")
/**
 * 如果用的是这种方式, com.MyWebApplicationInitializer#onStartup(javax.servlet.ServletContext)中:
 * 调用ac.refresh(); 会报错 No ServletContext set, 这里应该将这行代码注释,
 * 因为DispatcherServlet中会先ac.setServletContext(servletCxt);然后再refresh一遍, 所以建议之前ac.refresh()都不用加;
 * org.springframework.web.servlet.FrameworkServlet#configureAndRefreshWebApplicationContext(org.springframework.web.context.ConfigurableWebApplicationContext)
 * 或者用以下方式:通过实现WebMvcConfigurer接口
 *
 */
//public class AppConfig extends WebMvcConfigurationSupport {

/**
 * 在Java配置中，可以使用@EnableWebMvc注释来启用MVC配置 等同于: <mvc:annotation-driven>
 * 官方文档:https://docs.spring.io/spring/docs/5.2.4.RELEASE/spring-framework-reference/web.html#mvc-config-enable
 */
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {



    // 官方文档:https://docs.spring.io/spring/docs/5.2.4.RELEASE/spring-framework-reference/web.html#mvc-config

    /**
     * 配置视图解析器
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/page/", ".html");
    }

    /**
     * 添加自定义参数解析器
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(0, new ZhaoArgumentResolver());
    }

    /**
     * 配置消息转换器
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new FastJsonHttpMessageConverter());
    }
}
