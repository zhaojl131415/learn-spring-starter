package com.spring;

import javax.annotation.PostConstruct;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2021-08-09 16:09
 */
public class ZhaoApplicationContext {

    private Class configClass;

    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private List<BeanPostProccessor> beanPostProccessorList = new ArrayList<>();

    public ZhaoApplicationContext(Class configClass) {
        this.configClass = configClass;

        // 扫描
        scan(configClass);

        for (Map.Entry<String, BeanDefinition> bdEntry : beanDefinitionMap.entrySet()) {
            BeanDefinition beanDefinition = bdEntry.getValue();
            if (Objects.equals("singleton", beanDefinition.getScope())) {
                Object bean = createBean(bdEntry.getKey(), beanDefinition);
                singletonObjects.put(bdEntry.getKey(), bean);
            }
        }
    }

    private void scan(Class configClass) {
        // 解析ComponentScan, 获取需要扫描的包路径
        ComponentScan componentScanAnnotation = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
        String scanPath = componentScanAnnotation.value();
//        System.out.println(scanPath);

        // 扫描路径
        scanPath = scanPath.replaceAll("\\.", "/");

        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(scanPath);

        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            scanFile(classLoader, file);
        }
    }

    private void scanFile(ClassLoader classLoader, File file) {
        File[] files = file.listFiles();
        // 遍历扫描
        for (File f : files) {
            if (f.isDirectory()) {
                scanFile(classLoader, f);
            }
            String filePath = f.getAbsolutePath();
            if (filePath.endsWith(".class")) {
                String className = filePath.substring(filePath.indexOf("com"), filePath.indexOf(".class"));
                className = className.replaceAll("/", "\\.");
//                    System.out.println(className);

                try {
                    Class<?> clazz = classLoader.loadClass(className);
                    if (clazz.isAnnotationPresent(Component.class)) {
                        /**
                         * 判断当前class是否实现了BeanPostProccessor接口,
                         * 与instanceof的区别在于: instanceof用于实例化之后的实例对象, isAssignableFrom用于class文件.
                         */
                        if (BeanPostProccessor.class.isAssignableFrom(clazz)) {
                            BeanPostProccessor instance = (BeanPostProccessor) clazz.getDeclaredConstructor().newInstance();
                            beanPostProccessorList.add(instance);
                        }


                        BeanDefinition beanDefinition = new BeanDefinition();
                        beanDefinition.setClazz(clazz);

                        Component componentAnnotation = (Component) clazz.getDeclaredAnnotation(Component.class);
                        String beanName = componentAnnotation.value();


                        if (clazz.isAnnotationPresent(Scope.class)) {
                            Scope scopeAnnotation = (Scope) clazz.getDeclaredAnnotation(Scope.class);
                            beanDefinition.setScope(scopeAnnotation.value());
                        } else {
                            beanDefinition.setScope("singleton");
                        }
                        beanDefinitionMap.put(beanName, beanDefinition);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getClazz();
        Object instance = null;
        try {
            // 实例化
            instance = clazz.getDeclaredConstructor().newInstance();

            // 依赖注入
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object bean = getBean(field.getName());
                    field.setAccessible(true);
                    field.set(instance, bean);
                }
            }

//            for (Method method : clazz.getDeclaredMethods()) {
//                if (method.isAnnotationPresent(PostConstruct.class)) {
//                    method.invoke(instance, null);
//                }
//            }

            // Aware回调
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            // BeanPostProcessor
            for (BeanPostProccessor proccessor : beanPostProccessorList) {
                instance = proccessor.postProcessBeforeInitialization(instance, beanName);
            }

            // 初始化
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }

            // BeanPostProcessor
            for (BeanPostProccessor proccessor : beanPostProccessorList) {
                instance = proccessor.postProcessAfterInitialization(instance, beanName);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    public Object getBean(String beanName) {
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (Objects.equals("singleton", beanDefinition.getScope())) {
                return singletonObjects.get(beanName);
            } else {
                // 创建bean对象
                return createBean(beanName, beanDefinition);
            }
        } else {
            throw new NullPointerException();
        }
    }
}
