package com.seliote.fr.config.api;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.seliote.fr.annotation.stereotype.ApiAdvice;
import com.seliote.fr.annotation.stereotype.ApiComponent;
import com.seliote.fr.annotation.stereotype.ApiController;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * API 上下文
 *
 * @author seliote
 */
@Log4j2
@Configuration
@ComponentScan(basePackages = {"com.seliote.fr"}, useDefaultFilters = false, includeFilters = @ComponentScan.Filter(
        {ApiController.class, ApiAdvice.class, ApiComponent.class}))
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableWebMvc
public class ApiContext implements WebMvcConfigurer {
    private final JsonMapper jsonMapper;
    private final SpringValidatorAdapter springValidatorAdapter;

    @Autowired
    public ApiContext(JsonMapper jsonMapper,
                      SpringValidatorAdapter springValidatorAdapter) {
        this.jsonMapper = jsonMapper;
        this.springValidatorAdapter = springValidatorAdapter;
        log.debug("Construct {}", getClassName(this));
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // Spring 5.2.4+ favorPathExtension() 是 @Deprecated 的，且默认为 false
        configurer.favorParameter(false)
                .ignoreAcceptHeader(true)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON);
        log.debug("Override configureContentNegotiation(ContentNegotiationConfigurer)");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        var jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(jsonMapper);
        jsonConverter.setDefaultCharset(StandardCharsets.UTF_8);
        jsonConverter.setPrettyPrint(true);
        jsonConverter.setSupportedMediaTypes(new ArrayList<>() {{
            add(MediaType.APPLICATION_JSON);
        }});
        converters.add(jsonConverter);
        log.debug("Override configureMessageConverters(List<HttpMessageConverter<?>>)");
    }

    @Override
    public Validator getValidator() {
        log.debug("Override getValidator()");
        return springValidatorAdapter;
    }

    /**
     * 创建 MethodValidationPostProcessor Bean
     * BeanPostProcessor 不会从父上下文中继承
     * 该上下文为了完成 Bean 校验也需要定义一个 MethodValidationPostProcessor
     *
     * @return MethodValidationPostProcessor 对象
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        var methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(springValidatorAdapter);
        log.debug("Create bean {}", getClassName(methodValidationPostProcessor));
        return methodValidationPostProcessor;
    }
}
