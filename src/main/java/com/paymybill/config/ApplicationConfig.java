package com.paymybill.config;

import com.paymybill.handler.HibernateAwareObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.hibernate5.support.OpenSessionInViewInterceptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan("com.paymybill.*")
@PropertySource("classpath:app.properties")
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ApplicationContext context;

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertiesBean() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/").setCachePeriod(31556926);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
    }

    @Bean
    public RequestMappingHandlerMapping handlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
        handlerMapping.setInterceptors(localeChangeInterceptor());
        return handlerMapping;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        OpenSessionInViewInterceptor sessionInViewInterceptor = new OpenSessionInViewInterceptor();
        sessionInViewInterceptor.setSessionFactory(sessionFactory);

        registry.addWebRequestInterceptor(sessionInViewInterceptor);
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ret = new ReloadableResourceBundleMessageSource();
        ret.setBasenames("/WEB-INF/labels", "/WEB-INF/classes/validationMessages", "/WEB-INF/exceptions");
        ret.setDefaultEncoding(UTF8.name());
        return ret;
    }

    @Bean
    public CookieLocaleResolver localeResolver() {
        Locale ruLocale = new Locale.Builder().setLanguage("ru").build();
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(ruLocale);
        cookieLocaleResolver.setCookieName("localeCookie");
        cookieLocaleResolver.setCookieMaxAge(3600);

        return cookieLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        // TODO locale
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("locale");
        localeChangeInterceptor.setHttpMethods(HttpMethod.GET.name(), HttpMethod.POST.name());
        return localeChangeInterceptor;
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
        super.configureMessageConverters(converters);
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean lvfb = new LocalValidatorFactoryBean();
        lvfb.setValidationMessageSource(messageSource());
        return lvfb;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        // TODO size
        multipartResolver.setMaxUploadSize(500000000);
        return multipartResolver;
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(
                new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), UTF8),
                new MediaType(MediaType.IMAGE_JPEG.getType(), MediaType.IMAGE_JPEG.getSubtype(), UTF8),
                new MediaType(MediaType.IMAGE_PNG.getType(), MediaType.IMAGE_PNG.getSubtype(), UTF8),
                new MediaType(MediaType.MULTIPART_FORM_DATA.getType(), MediaType.MULTIPART_FORM_DATA.getSubtype(), UTF8)
        ));
        HibernateAwareObjectMapper hibernateAwareObjectMapper = new HibernateAwareObjectMapper();
        converter.setObjectMapper(hibernateAwareObjectMapper);
        return converter;
    }

    @Bean
    public SimpleMappingExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        // TODO продумать маппинги эксепшенов
        exceptionMappings.put("org.springframework.security.web.authentication.rememberme.CookieTheftException", "user/login?error=sessionExpired");
        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");

        exceptionResolver.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();

        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");

        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().excludeFieldsWithModifiers(Modifier.PRIVATE).create();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }
}