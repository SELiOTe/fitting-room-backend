package com.seliote.fr.config;

import com.seliote.fr.config.api.ApiContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;

/**
 * Spring 框架启动类
 *
 * @author seliote
 */
@Log4j2
public class Bootstrap implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        // 静态资源处理 Servlet
        servletContext.getServletRegistration("default").addMapping("/static/*");
        // 根应用上下文
        var rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootContext.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
        // API 上下文
        var apiContext = new AnnotationConfigWebApplicationContext();
        apiContext.register(ApiContext.class);
        var apiServlet = new DispatcherServlet(apiContext);
        apiServlet.setThrowExceptionIfNoHandlerFound(true);
        var apiReg = servletContext.addServlet("api-servlet", apiServlet);
        apiReg.setLoadOnStartup(1);
        apiReg.addMapping("/");
        // Spring Security 过滤器
        var delegateFilter = new DelegatingFilterProxy(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
        servletContext.addFilter("spring-security-filter", delegateFilter)
                .addMappingForUrlPatterns(null, false, "/*");
        // 注册 RequestContextListener
        servletContext.addListener(new RequestContextListener());
        // 完成注册
        log.debug("Override onStartup(ServletContext)");
    }
}
