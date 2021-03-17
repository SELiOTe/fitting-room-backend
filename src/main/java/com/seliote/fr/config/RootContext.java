package com.seliote.fr.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.seliote.fr.annotation.stereotype.ApiAdvice;
import com.seliote.fr.annotation.stereotype.ApiComponent;
import com.seliote.fr.annotation.stereotype.ApiController;
import com.seliote.fr.config.auth.AuthContext;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.Assert;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.Executor;

import static com.seliote.fr.util.ReflectUtils.getClassName;
import static com.seliote.fr.util.ReflectUtils.getMethodName;

/**
 * 根应用上下文
 *
 * @author seliote
 */
@Log4j2
@Configuration
@ComponentScan(basePackages = {"com.seliote.fr"}, excludeFilters = @ComponentScan.Filter(
        {Configuration.class, ApiController.class, ApiAdvice.class, ApiComponent.class}))
@EnableAsync(proxyTargetClass = true, order = Ordered.HIGHEST_PRECEDENCE)
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@MapperScan(basePackages = {"com.seliote.fr.mapper"})
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource({"classpath:application.properties"})
@Import({AuthContext.class})
public class RootContext implements AsyncConfigurer, SchedulingConfigurer {

    // 线程池日志记录器
    private static final Logger THREAD_POOL_LOGGER = LogManager.getLogger(log.getName() + "[THREAD-POOL]");

    // 应用属性注入
    @Value("${sys.payload}")
    private int sysPayload;
    @Value("${db.jdbc-url}")
    private String dbJdbcUrl;
    @Value("${db.user}")
    private String dbUser;
    @Value("${db.password}")
    private String dbPassword;
    @Value("${db.query-timeout}")
    private int dbQueryTimeout;
    @Value("${db.max-pool-size}")
    private int dbMaxPoolSize;
    @Value("${db.coon-timeout}")
    private long dbCoonTimeout;
    @Value("${db.max-lifetime}")
    private long dbMaxLifetime;
    @Value("${db.test-query}")
    private String dbTestQuery;


    @Autowired
    public RootContext() {
        log.debug("Construct {}", getClassName(this));
    }

    /**
     * 创建静态 PropertySourcesPlaceholderConfigurer Bean，用于 @Value() 属性注入
     *
     * @return PropertySourcesPlaceholderConfigurer 对象
     */
    @Bean
    private static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        var propertySources = new PropertySourcesPlaceholderConfigurer();
        log.debug("Create static bean {}", getClassName(propertySources));
        return propertySources;
    }

    @Override
    public Executor getAsyncExecutor() {
        log.debug("Override getAsyncExecutor()");
        return threadPoolTaskScheduler();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        log.debug("Override getAsyncUncaughtExceptionHandler()");
        return (throwable, method, objects) -> THREAD_POOL_LOGGER.error(
                "Thread pool exception, method: {}, arguments: {}, occur: {}, message: {}",
                getMethodName(method), Arrays.toString(objects), getClassName(throwable), throwable.getMessage());
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler());
        log.debug("Override configureTasks(ScheduledTaskRegistrar)");
    }

    /**
     * 创建 ThreadPoolTaskScheduler Bean，同时支持 TaskExecutor 与 TaskScheduler 类型注入
     *
     * @return ThreadPoolTaskScheduler 对象
     */
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        var threadPool = new ThreadPoolTaskScheduler();
        threadPool.setPoolSize(sysPayload);
        threadPool.setThreadNamePrefix("[THREAD-POOL]");
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.setAwaitTerminationSeconds(sysPayload + 3);
        threadPool.setRejectedExecutionHandler((runnable, threadPoolExecutor) -> THREAD_POOL_LOGGER.error(
                "Thread pool: {}, reject: {}", threadPoolExecutor, runnable));
        threadPool.setErrorHandler(throwable -> THREAD_POOL_LOGGER.error(
                "Thread pool occur: {}, message: {}", getClassName(throwable), throwable.getMessage()));
        log.debug("Create bean {}", getClassName(threadPool));
        return threadPool;
    }

    /**
     * 创建 JsonMapper Bean
     *
     * @return JsonMapper 对象
     */
    @Bean
    public JsonMapper jsonMapper() {
        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        var dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        var javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter))
                .addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        var jsonMapper = JsonMapper.builder()
                .deactivateDefaultTyping()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
                .addModules(javaTimeModule)
                .build();
        log.debug("Create bean {}", getClassName(jsonMapper));
        return jsonMapper;
    }

    /**
     * 创建 HikariDataSource Bean
     *
     * @return HikariDataSource 对象
     */
    @Bean
    public HikariDataSource hikariDataSource() {
        final var driverName = "org.mariadb.jdbc.Driver";
        var hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("[MARIADB-HIKARI]");
        hikariConfig.setDriverClassName(driverName);
        hikariConfig.setJdbcUrl(dbJdbcUrl);
        hikariConfig.setUsername(dbUser);
        hikariConfig.setPassword(dbPassword);
        dbJdbcUrl = dbUser = dbPassword = null;
        hikariConfig.setMaximumPoolSize(dbMaxPoolSize);
        hikariConfig.setConnectionTimeout(dbCoonTimeout);
        hikariConfig.setMaxLifetime(dbMaxLifetime);
        hikariConfig.setConnectionTestQuery(dbTestQuery);
        var hikariDataSource = new HikariDataSource(hikariConfig);
        log.debug("Create bean {}", getClassName(hikariDataSource));
        return hikariDataSource;
    }

    /**
     * 创建 SqlSessionFactory Bean
     *
     * @return SqlSessionFactory 对象
     * @throws Exception 创建 Bean 异常时抛出
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        var sqlSessionFactoryBean = new SqlSessionFactoryBean();
        var configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setDefaultStatementTimeout(dbQueryTimeout);
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setDataSource(hikariDataSource());
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        var sqlSessionFactory = sqlSessionFactoryBean.getObject();
        Assert.notNull(sqlSessionFactory, "MyBatis SqlSessionFactory must not be null");
        log.debug("Create bean {}", getClassName(sqlSessionFactory));
        return sqlSessionFactory;
    }

    /**
     * 创建 TransactionManager Bean
     *
     * @return TransactionManager 对象
     */
    @Bean
    public TransactionManager transactionManager() {
        var dataSourceTransactionManager = new DataSourceTransactionManager(hikariDataSource());
        log.debug("Create bean {}", getClassName(dataSourceTransactionManager));
        return dataSourceTransactionManager;
    }

    /**
     * 创建 LocalValidatorFactoryBean Bean
     *
     * @return LocalValidatorFactoryBean 对象
     * @throws ClassNotFoundException JSR 380 实现无法加载时抛出
     */
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() throws ClassNotFoundException {
        var localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(Class.forName("org.hibernate.validator.HibernateValidator"));
        log.debug("Create bean {}", getClassName(localValidatorFactoryBean));
        return localValidatorFactoryBean;
    }

    /**
     * 创建 MethodValidationPostProcessor Bean
     *
     * @return MethodValidationPostProcessor 对象
     * @throws ClassNotFoundException JSR 380 实现无法加载时抛出
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() throws ClassNotFoundException {
        var methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(localValidatorFactoryBean());
        log.debug("Create bean {}", getClassName(methodValidationPostProcessor));
        return methodValidationPostProcessor;
    }
}
