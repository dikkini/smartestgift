package com.smartestgift;

import com.smartestgift.config.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by dikkini on 18.03.14.
 * Email: dikkini@gmail.com
 */
public class Initializer implements WebApplicationInitializer {

    // gets invoked automatically when application starts up
    public void onStartup(ServletContext servletContext) throws ServletException {

        // Create ApplicationContext. I'm using the
        // AnnotationConfigWebApplicationContext to avoid using beans xml files.
        AnnotationConfigWebApplicationContext ctx =
                new AnnotationConfigWebApplicationContext();
        ctx.register(WebConfig.class);

        // Add the servlet mapping manually and make it initialize automatically
        ServletRegistration.Dynamic servlet =
                servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
    }

}