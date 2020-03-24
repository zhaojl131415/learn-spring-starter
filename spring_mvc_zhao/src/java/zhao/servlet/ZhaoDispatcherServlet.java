package com.zhao.servlet;

import com.zhao.annotation.ZhaoController;
import com.zhao.annotation.ZhaoRequestMapping;
import com.zhao.annotation.ZhaoResponseBody;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-22 14:14
 */
public class ZhaoDispatcherServlet extends HttpServlet {


    private static String COMPONENT_SCAN_ELEMENT_NAME = "componentScan";
    private static String COMPONENT_SCAN_ELEMENT_PACKAGE_NAME = "package";

    private static String XML_PATH = "xmlPath";

    private static String projectPath = ZhaoDispatcherServlet.class.getResource("/").getPath();

    private static String prefix = "";
    private static String suffix = "";

    private static Map<String, Method> methodMap = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //拿到请求的URI
        String requestURI = req.getRequestURI();
        Method method = methodMap.get(requestURI);
        if (method != null) {
            //jdk8以前 直接拿参数名称 拿不到
            Parameter[] parameters = method.getParameters();
            Object[] objects = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                // arg0
                // jdk8 以后在 Preferences | Build, Execution, Deployment | Compiler | Java Compiler 的Additional command line parameters添加-parameters参数
                // 实际上springmvc是操作字节码文件
                String name = parameter.getName();
                Class type = parameter.getType();

                if (type.equals(String.class)) {
                    objects[i] = req.getParameter(name);
                } else if (type.equals(HttpServletRequest.class)) {
                    objects[i] = req;
                } else if (type.equals(HttpServletResponse.class)) {
                    objects[i] = resp;
                } else {
                    try {
                        Object o = type.newInstance();
//                        type.getDeclaredConstructor().newInstance()
                        for (Field field : type.getDeclaredFields()) {
                            field.setAccessible(true);
                            String fieldName = field.getName();
                            field.set(o, req.getParameter(fieldName));
                        }
                        objects[i] = o;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                Object o = method.getDeclaringClass().newInstance();
                Object invoke = method.invoke(o, objects);
                // 判断返回值是否是Void
                if (!method.getReturnType().equals(Void.class)) {
                    ZhaoResponseBody annotation = method.getAnnotation(ZhaoResponseBody.class);
                    if (annotation != null) {
                        //提供接口来做这个事情
                        resp.getWriter().write(String.valueOf(invoke));
                    } else {
                        // /page/index.html   page/index.html
                        req.getRequestDispatcher(prefix + String.valueOf(invoke) + suffix).forward(req, resp);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            resp.setStatus(404);
        }


//        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 解析xml  web.xml  zhao-mvc.xml(spring-mvc.xml)
//        try {
//            projectPath = URLDecoder.decode(projectPath, null);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        String initParameter = config.getInitParameter(XML_PATH);
        //解析xml文件 file:xml 文件对象
        String filePath = projectPath + initParameter;

        Document prase = prase(new File(filePath));
        Element rootElement = prase.getRootElement();
        Element view = rootElement.element("view");
        prefix = view.attribute("prefix").getValue();
        suffix = view.attribute("suffix").getValue();


        Element componentScanEle = rootElement.element(COMPONENT_SCAN_ELEMENT_NAME);
        String value = componentScanEle.attribute(COMPONENT_SCAN_ELEMENT_PACKAGE_NAME).getValue();
        value = value.replaceAll("\\.", File.separator);
        // 扫描项目
        doScan(new File(projectPath + value));

//        super.init(config);
    }

    /**
     * 解析xml文件
     *
     * @param file xml文件
     * @return
     */
    public Document prase(File file) {
        SAXReader saxReader = new SAXReader();
        try {
            return saxReader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 扫描
     *
     * @param file
     */
    public void doScan(File file) {
        // 如果是目录
        if (file.isDirectory()) {
            // 遍历对象
            for (File f : file.listFiles()) {
                // 递归
                doScan(f);
            }
        } else {
            // filePath:/Users/zhaojinliang/Code/Source/test-mvc/target/classes/com/zhao/controller/DemoController.class
            String filePath = file.getPath();
            // 获取扫描到的文件后缀名
            String suffix = filePath.substring(filePath.lastIndexOf("."));
            // 如果为class文件
            if (suffix.equals(".class")) {
                // 去掉根目录地址, 得到: com/zhao/controller/DemoController.class
                String classPath = filePath.replace(new File(projectPath).getPath() + File.separator, "");
                // 替换/, 得到: com.zhao.controller.DemoController.class
                classPath = classPath.replaceAll(File.separator, ".");
                String className = classPath.substring(0, classPath.lastIndexOf("."));
                try {
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(ZhaoController.class)) {
                        // 获取类上的@ZhaoRequestMapping注解
                        ZhaoRequestMapping classRequestMapping = clazz.getAnnotation(ZhaoRequestMapping.class);
                        // 如果类上的@ZhaoRequestMapping注解不为空, 获取注解的value, 否则返回空
                        String classRequestMappingUrl = classRequestMapping != null ? classRequestMapping.value() : "";
                        // 遍历classs的方法
                        for (Method method : clazz.getDeclaredMethods()) {
                            // 不是合成方法
                            if (!method.isSynthetic()) {
                                // 获取方法上的@ZhaoRequestMapping注解
                                ZhaoRequestMapping annotation = method.getAnnotation(ZhaoRequestMapping.class);
                                if (annotation != null) {
                                    String methodRequestMappingUrl = annotation.value();
                                    System.out.println("类:" + clazz.getName() + "的" + method.getName() + "方法被映射到了" + classRequestMappingUrl + methodRequestMappingUrl + "上面");
                                    methodMap.put(classRequestMappingUrl + methodRequestMappingUrl, method);
                                }

                            }
                        }
                    }
                } catch (ClassNotFoundException e) {

                }
            }
        }
    }
}
