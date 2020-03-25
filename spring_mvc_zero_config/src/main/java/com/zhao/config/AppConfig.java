package com.zhao.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zhao.argResolver.ZhaoArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
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
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/page/", ".html");
    }

    /**
     * 添加自定义返回值处理器
     * @param handlers
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        // 参照添加自定义参数解析器
    }

    /**
     * 添加自定义参数解析器
     *
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 当前这个resolvers 和spring mvc里面用resolvers不是同一个集合,
        // 最后是通过spring mvc里面的resolvers.addAll(当前resolvers)的方式整合在一起argumentResolvers.addAll(customResolvers);
        // 所以这里添加索引为0, 也无法让此自定义参数解析器第一个生效
        // 但是这个问题没有复现, 可能是早期版本的bug, 现在已经修复了,
        resolvers.add(new ZhaoArgumentResolver());
    }


//    @Autowired //Spring mvc 环境初始化完之后 去加载这个方法
////    @Bean //不行  因为这时候spring mvc 容器还没有初始化完成, 会报错 说没有RequestMappingHandlerAdapter
//    public void initArgumentResolvers(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
//        // 所有参数解析器
//        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>(requestMappingHandlerAdapter.getArgumentResolvers());
//        // 所有自定义的参数解析器
//        List<HandlerMethodArgumentResolver> customResolvers = requestMappingHandlerAdapter.getCustomArgumentResolvers();
//        // 剔除掉自定义的参数解析器
//        argumentResolvers.removeAll(customResolvers);
//        // 然后把自定义的参数解析器加入到头部
//        argumentResolvers.addAll(0, customResolvers);
//        // 重新设置参数解析器
//        requestMappingHandlerAdapter.setArgumentResolvers(argumentResolvers);
//    }

    /**
     * 配置消息转换器
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new FastJsonHttpMessageConverter());
    }


}
