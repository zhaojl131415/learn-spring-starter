package com.zhao.mybatis;

import com.zhao.mybatis.config.MybatisAppConfig;
import com.zhao.mybatis.factory.ZhaoSessionFactory;
import com.zhao.mybatis.mapper.UserMapper;
import com.zhao.mybatis.service.UserService;
import com.zhao.mybatis.utils.ZhaoScanner;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-05-22 21:07
 */
public class MybatisTest {
    public static void main(String[] args) {
//        DataSource dataSource = null;
//        TransactionFactory transactionFactory =
//                new JdbcTransactionFactory();
//        Environment environment =
//                new Environment("development", transactionFactory, dataSource);
//        Configuration configuration = new Configuration(environment);
//        configuration.addMapper(UserMapper.class);
//        SqlSessionFactory sqlSessionFactory =
//                new SqlSessionFactoryBuilder().build(configuration);
//
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//        System.out.println(mapper.queryUser());


//        UserMapper mapper = (UserMapper) ZhaoSessionFactory.getMapper(UserMapper.class);
//        System.out.println(mapper.queryUser());


        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MybatisAppConfig.class);
        ac.refresh();


//        ZhaoScanner scanner = new ZhaoScanner(ac);
//        scanner.addIncludeFilter(null);
//        int scan = scanner.scan("com.zhao.mybatis");
//        System.out.println(scan);

        // 能够得到一个实现了UserMapper的代理对象
        UserService userService = ac.getBean(UserService.class);

        // 完成了查询
        userService.queryUser();
    }
}
