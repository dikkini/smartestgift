package com.smartestgift.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartestgift.handler.UserInterceptor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.support.OpenSessionInViewInterceptor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by dikkini on 18.03.14.
 * Email: dikkini@gmail.com
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.smartestgift"})
@EnableTransactionManagement
public class WebConfig extends WebMvcConfigurerAdapter {
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
        dataSource.setDriverClassName("jdbc.driverClassName");
        dataSource.setUrl("jdbc.databaseurl");
        dataSource.setUsername("jdbc.username");
        dataSource.setPassword("jdbc.password");

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
        emf.setPackagesToScan("com.smartestgift.dao.model");

        //let Hibernate know which database we're using.
        //note that this is vendor specific, not JPA
        Map opts = emf.getJpaPropertyMap();
        opts.put("hibernate.dialect", env.getProperty("hibernate.dialect"));

        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(va);

        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets").setCachePeriod(31556926);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/", "/login/**", "/signup/**");
        OpenEntityManagerInViewInterceptor interceptor = new OpenEntityManagerInViewInterceptor();
        interceptor.setEntityManagerFactory(entityManagerFactory().getObject());
        registry.addWebRequestInterceptor(interceptor);
    }

    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    private Properties hibernateProperties() {
        return new Properties() {{
            setProperty("hibernate.connection.characterEncoding", "jdbc.connectCharSet");
            setProperty("hibernate.connection.charSet", "jdbc.connectCharSet");
            setProperty("hibernate.dialect", "jdbc.dialect");
            setProperty("hibernate.show_sql", "jdbc.show_sql");
        }};
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource rrbms = new ReloadableResourceBundleMessageSource();
        rrbms.setBasename("classpath:label");
        rrbms.setDefaultEncoding("UTF-8");
        return rrbms;
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
    }
}