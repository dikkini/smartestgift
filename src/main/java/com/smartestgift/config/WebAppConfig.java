package com.smartestgift.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartestgift.handler.CurrentUserHandlerMethodArgumentResolver;
import com.smartestgift.handler.UserInterceptor;
import javassist.Modifier;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by dikkini on 18.03.14.
 * Email: dikkini@gmail.com
 */

@Configuration
@EnableWebMvc
@ComponentScan("com.smartestgift")
@EnableTransactionManagement
public class WebAppConfig extends WebMvcConfigurerAdapter {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Autowired
    Environment env;

    //Enable serving static resources even when DispatcherServlet is mapped to /
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");

        dataSource.setUrl("jdbc:postgresql://109.120.165.66:5432/smartestgiftdb?useEncoding=true&amp;characterEncoding=UTF-8");
        dataSource.setUsername("smartestgiftdbadmin");
        dataSource.setPassword("smartestgiftdbadmin");

/*        dataSource.setUrl("jdbc:postgresql://172.25.29.197:5432/smartestgiftdb?useEncoding=true&amp;characterEncoding=UTF-8");
        dataSource.setUsername("dikkini");
        dataSource.setPassword("asddsa");*/

        return dataSource;
    }

    @Bean
    LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.smartestgift.*");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    //Set up JPA and transactionManager
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("com.smartestgift.*");

        //let Hibernate know which database we're using.
        //note that this is vendor specific, not JPA
        Map opts = emf.getJpaPropertyMap();
        opts.put("hibernate.dialect", env.getProperty("hibernate.dialect"));

        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(va);

        return emf;
    }

    @Bean
    @Autowired
    PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/").setCachePeriod(31556926);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        OpenEntityManagerInViewInterceptor interceptor = new OpenEntityManagerInViewInterceptor();
        interceptor.setEntityManagerFactory(entityManagerFactory().getObject());

        registry.addWebRequestInterceptor(interceptor);
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/", "/login/**", "/signup/**");
    }

    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    private Properties hibernateProperties() {
        return new Properties() {{
            setProperty("hibernate.connection.characterEncoding", "UTF-8");
            setProperty("hibernate.connection.charSet", "UTF-8");
            setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
            setProperty("hibernate.show_sql", "true");
        }};
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource ret = new ReloadableResourceBundleMessageSource();
        ret.setBasename("/assets/messages");
        ret.setDefaultEncoding("UTF-8");
        return ret;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver ret = new CookieLocaleResolver();
        ret.setDefaultLocale(Locale.ENGLISH);
        return ret;
    }

    @Bean
    public HandlerMapping handlerMapping() {
        final DefaultAnnotationHandlerMapping ret = new DefaultAnnotationHandlerMapping();
        ret.setInterceptors(new Object[] { localeChangeInterceptor() });
        return ret;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserHandlerMethodArgumentResolver());
    }

    @Bean
    public CurrentUserHandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver() {
        return new CurrentUserHandlerMethodArgumentResolver();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("text", "json", UTF8),
                new MediaType("image", "jpeg", UTF8),
                new MediaType("image", "png", UTF8)
        ));
        stringConverter.setWriteAcceptCharset(false);
        converters.add(stringConverter);
        converters.add(jackson2HttpMessageConverter());
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "json", UTF8)
        ));
        return converter;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().excludeFieldsWithModifiers(Modifier.PRIVATE).create();
    }
}