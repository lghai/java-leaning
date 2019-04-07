package com.springboot.springmvc.conf;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyWebAppinitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Spring IoC 容器配置 必须要有@Configuration注解和@ComponentScan注解
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};
    }

    /**
     * DispatcherServlet 的 URI 映射关系配置 必须要有@Configuration注解和@ComponentScan注解
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{ WebConfig.class };
    }
    // DispatcherServlet 拦截请求匹配
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}