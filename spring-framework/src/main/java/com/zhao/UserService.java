package com.zhao;

import com.spring.Autowired;
import com.spring.BeanNameAware;
import com.spring.Component;
import com.spring.InitializingBean;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2021-08-09 16:21
 */
@Component("userService")
public class UserService implements IUserService, BeanNameAware, InitializingBean {

    @Autowired
    private OrderService orderService;

    private String beanName;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("bean初始化");
    }

    public void test() {
        System.out.println(orderService);
        System.out.println(beanName);
        System.out.println(name);
    }


    @Override
    public void testAop() {
        System.out.println("实际业务逻辑");
    }

}
