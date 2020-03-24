package com.zhao.init;

import com.zhao.config.AppConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * spring官网: springmvc 实现0xml配置
 * https://docs.spring.io/spring/docs/5.2.4.RELEASE/spring-framework-reference/web.html#mvc
 */
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    /**
     * 步骤:
     * 提供一个类实现spring的接口:WebApplicationInitializer
     *
     * tomcat启动时会调用 onStartup.   为什么tomcat会调用???
     * 因为servlet3.0的一个新规范:spi, tomcat也是这个规范实现方之一, 包括jetty在内的其他web容器也是
     * spring-web-5.0.8.RELEASE.jar!/META-INF/services/javax.servlet.ServletContainerInitializer文件内容为:
     * org.springframework.web.SpringServletContainerInitializer
     *
     * @HandlesTypes(WebApplicationInitializer.class)
     * public class SpringServletContainerInitializer implements ServletContainerInitializer {
     *
     * 我们找到对应的SpringServletContainerInitializer文件可以看见 它实现了ServletContainerInitializer接口,
     * 而这个ServletContainerInitializer接口是javax.servlet目录下的接口, 他两都有个onStartup()方法
     * tomcat作为实现方之一, 会通过ServletContainerInitializer找到实现类SpringServletContainerInitializer, 调用onStartup()方法,
     * 如果项目里有多处需要在tomcat启动时调用, 就需要实现SpringServletContainerInitializer类中@HandlesTypes注解中的WebApplicationInitializer接口,
     * 会在调用onStartup()方法时, 扫描所有WebApplicationInitializer的实现类, 调用这些实现类的onStartup()方法
     *
     * SpringServletContainerInitializer
     *
     *
     *
     * 调用onStartup需要传入一个ServletContext: web上下文对象, 相当于web.xml
     */

    @Override
    public void onStartup(ServletContext servletCxt) {
        // Load Spring web application configuration
        // 以注解方式初始化spring容器
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        // 注册配置类
        ac.register(AppConfig.class);
//        ac.setServletContext(servletCxt);
//        ac.refresh(); //这个需要注释, 官方没注释

        //
        // Create and register the DispatcherServlet 创建一个DispatcherServlet(springmvc核心), 并传入spring上下文
        DispatcherServlet servlet = new DispatcherServlet(ac);
        // 把DispatcherServlet加到ServletContext
        ServletRegistration.Dynamic registration = servletCxt.addServlet("app", servlet);
        /**
         * 等同于web.xml中如下:
         * <servlet>
         *         <servlet-name>app</servlet-name>
         *         <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
         *         <init-param>
         *             <param-name>contextConfigLocation</param-name>
         *             <param-value></param-value>
         *         </init-param>
         *         <load-on-startup>1</load-on-startup>
         *     </servlet>
         */
        registration.setLoadOnStartup(1);

//        registration.setInitParameter("contextConfigLocation","classpath:spring-mvc.xml");

        /**
         * 等同于web.xml中如下:
         * <servlet-mapping>
         *         <servlet-name>app</servlet-name>
         *         <url-pattern>/*</url-pattern>
         *     </servlet-mapping>
         */
//        registration.addMapping("/*");
        registration.addMapping("*.do");

    }
}