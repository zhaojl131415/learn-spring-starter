package com.zhao.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-07-17 11:27
 */
public class ServletDemo extends HttpServlet {

    /**
     * todo:
     * 1. 方法调用方: 谁会调用doGet()方法
     *      tomcat:因为当前类是要部署到tomcat中去的, 会在tomcat内部生成对应ServletDemo实例, 调用doGet()方法
     * 2. 方法传参: HttpServletRequest/HttpServletResponse都为接口, 传参为实现类, 会是什么样的实现类
     *      HttpServletRequest: tomcat提供具体的实现类: RequestFacade
     *      HttpServletResponse: ResponseFacade
     *
     * RequestFacade/ResponseFacade: 设计模式之门面模式
     * 类似代理模式: 代理只能代理一个, 门面可以代理多个
     *
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
