package com.smartestgift.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by dikkini on 19.03.14.
 * Email: dikkini@gmail.com
 */

@Configuration
@EnableWebSecurity
@ImportResource("classpath:security.xml")
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
}
