//package com.zhao.servlet.handler;
///*
// * Copyright 2002-2018 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//import java.lang.reflect.Method;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Set;
//
//import javax.servlet.http.HttpServletRequest;
//
//
//import org.springframework.context.support.StaticApplicationContext;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
//import org.springframework.web.servlet.handler.HandlerMethodMappingNamingStrategy;
//import org.springframework.web.util.UrlPathHelper;
//
//
//
///**
// * Test for {@link AbstractHandlerMethodMapping}.
// *
// * @author Arjen Poutsma
// * @author Rossen Stoyanchev
// */
//@SuppressWarnings("unused")
//public class HandlerMethodMappingTests {
//
//	private AbstractHandlerMethodMapping<String> mapping;
//
//	private MyHandler handler;
//
//	private Method method1;
//
//	private Method method2;
//
//
//	public void setUp() throws Exception {
//		this.mapping = new MyHandlerMethodMapping();
//		//this.handler = new MyHandler();
////		this.method1 = handler.getClass().getMethod("handlerMethod1");
////		this.method2 = handler.getClass().getMethod("handlerMethod2");
//	}
//
//
//
//
//
//	public void detectHandlerMethodsInAncestorContexts1() {
//	    //当前的父容器
//		StaticApplicationContext cxt = new StaticApplicationContext();
//
//        cxt.registerSingleton("myHandler", MyHandler.class);
//        AbstractHandlerMethodMapping<String> mapping1 = new MyHandlerMethodMapping();
//
//        mapping1.setApplicationContext(new StaticApplicationContext(cxt));
//        mapping1.afterPropertiesSet();
//		System.out.println(mapping1.getHandlerMethods().size());
//		//这里会加载几个controller?   0
//
//		AbstractHandlerMethodMapping<String> mapping2 = new MyHandlerMethodMapping();
//		mapping2.setDetectHandlerMethodsInAncestorContexts(true);
//		mapping2.setApplicationContext(new StaticApplicationContext(cxt));
//		mapping2.afterPropertiesSet();
//		System.out.println(mapping2.getHandlerMethods().size());
//		//2
//
//	}
//
//	public static void main(String[] args)  throws Exception{
//		HandlerMethodMappingTests handlerMethodMappingTests = new HandlerMethodMappingTests();
//		handlerMethodMappingTests.setUp();
//		handlerMethodMappingTests.detectHandlerMethodsInAncestorContexts();
//
//	}
//
//
//    public void detectHandlerMethodsInAncestorContexts() {
//        //当前的父容器
//        ZhaoApplicationContext parent = new ZhaoApplicationContext();
//        //如果是单纯的Spring环境的话 那么你传了一个父容器, 在getBean的时候会尝试去父容器里面去寻找
//
//        //在父容器上面注册了Controller的2个方法 handle
//        parent.registerSingleton("myHandler", MyHandler.class);
//
//        //Spring mvc 加载Controller的类  这里可以理解为模拟spring mvc 的容器
//        AbstractHandlerMethodMapping<String> mapping1 = new MyHandlerMethodMapping();
//        //模拟早期他的父子容器的场景
//        ZhaoApplicationContext childContext = new ZhaoApplicationContext(parent);
//        //设置spring mvc 里面的容器为子容器
//        mapping1.setApplicationContext(childContext);
//        System.out.println(childContext.getBeanNamesForType(Object.class).length);
//
////        mapping1.setApplicationContext();
//        mapping1.afterPropertiesSet();
//        //因为现在spring mvc 已经默认禁用父子容器了 所以这里会打印一个0
//        System.out.println(mapping1.getHandlerMethods().size());
//        //这里会加载几个controller?   0
//        AbstractHandlerMethodMapping<String> mapping2 = new MyHandlerMethodMapping();
//        mapping2.setDetectHandlerMethodsInAncestorContexts(true);
//        mapping2.setApplicationContext(new ZhaoApplicationContext(parent));
//        mapping2.afterPropertiesSet();
//        System.out.println(mapping2.getHandlerMethods().size());
//        //2
//    }
//
//
//
//
//
//
//	private static class MyHandlerMethodMapping extends AbstractHandlerMethodMapping<String> {
//
//		private UrlPathHelper pathHelper = new UrlPathHelper();
//
//		private PathMatcher pathMatcher = new AntPathMatcher();
//
//
//		public MyHandlerMethodMapping() {
//			setHandlerMethodMappingNamingStrategy(new SimpleMappingNamingStrategy());
//		}
//
//		@Override
//		protected boolean isHandler(Class<?> beanType) {
//			return true;
//		}
//
//		@Override
//		protected String getMappingForMethod(Method method, Class<?> handlerType) {
//			String methodName = method.getName();
//			return methodName.startsWith("handler") ? methodName : null;
//		}
//
//		@Override
//		protected Set<String> getMappingPathPatterns(String key) {
//			return (this.pathMatcher.isPattern(key) ? Collections.<String>emptySet() : Collections.singleton(key));
//		}
//
//		@Override
//		protected CorsConfiguration initCorsConfiguration(Object handler, Method method, String mapping) {
//			CorsConfiguration corsConfig = new CorsConfiguration();
//			corsConfig.setAllowedOrigins(Collections.singletonList("http://" + handler.hashCode() + method.getName()));
//			return corsConfig;
//		}
//
//		@Override
//		protected String getMatchingMapping(String pattern, HttpServletRequest request) {
//			String lookupPath = this.pathHelper.getLookupPathForRequest(request);
//			return this.pathMatcher.match(pattern, lookupPath) ? pattern : null;
//		}
//
//		@Override
//		protected Comparator<String> getMappingComparator(HttpServletRequest request) {
//			String lookupPath = this.pathHelper.getLookupPathForRequest(request);
//			return this.pathMatcher.getPatternComparator(lookupPath);
//		}
//
//	}
//
//	private static class SimpleMappingNamingStrategy implements HandlerMethodMappingNamingStrategy<String> {
//
//		@Override
//		public String getName(HandlerMethod handlerMethod, String mapping) {
//			return handlerMethod.getMethod().getName();
//		}
//	}
//
//	@Controller
//	static class MyHandler {
//
//		public MyHandler(){
//			System.out.println("----------");
//		}
//
//		@RequestMapping
//		public void handlerMethod1() {
//		}
//
//		@RequestMapping
//		public void handlerMethod2() {
//		}
//	}
//}
