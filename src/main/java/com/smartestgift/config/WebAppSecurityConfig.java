package com.smartestgift.config;

import com.smartestgift.security.SmartTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Created by dikkini on 19.03.14.
 * Email: dikkini@gmail.com
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SmartTokenRepository tokenRepository;

//    @Autowired
//    private SmartLogoutHandler smartLogoutHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "SELECT username, password, enabled FROM users WHERE username=?")
                .authoritiesByUsernameQuery(
                        "SELECT username, role FROM user_roles WHERE username=?").passwordEncoder(new StandardPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/assets/**","/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.userDetailsService(userDetailsService());
        http.sessionManagement()
                .invalidSessionUrl("/session/invalid")
                .maximumSessions(1)
                .expiredUrl("/session/expired")
                .maxSessionsPreventsLogin(true);
        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profile?successes=login_success")
                .loginProcessingUrl("/j_spring_security_check")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .failureUrl("/login?errors=login_error");
        http.logout()
                .deleteCookies("JSESSIONID")
                .deleteCookies("REMEMBER_ME")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                .logoutUrl("/logout");
        http.authorizeRequests()
                .antMatchers("/admin*").hasRole("admin")
                .antMatchers("/assets*").permitAll()
                .antMatchers("/login/**", "/signup/**").anonymous()
                .antMatchers("/**").access("isRememberMe() or isFullyAuthenticated()")
                .anyRequest().authenticated();
/*        http.addFilter(logoutFilter());*/
        http.rememberMe()
                .rememberMeServices(rememberMeServices())
                .key("127roseQ");
        http.csrf()
                .disable();
        http.httpBasic();
    }

/*    @Bean
    public LogoutFilter logoutFilter() {
        return new LogoutFilter("/login", securityContextLogoutHandler(), smartLogoutHandler);
    }*/

    @Bean
    public SecurityContextLogoutHandler securityContextLogoutHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean
    public RememberMeServices rememberMeServices(){
        PersistentTokenBasedRememberMeServices rememberMeServices = new PersistentTokenBasedRememberMeServices(
                "127roseQ", userDetailsService(), tokenRepository);
        rememberMeServices.setCookieName("REMEMBER_ME");
        rememberMeServices.setParameter("remember");
        rememberMeServices.setTokenValiditySeconds(172800);
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider(){
        return new RememberMeAuthenticationProvider("127roseQ");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new SmartTokenRepository();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }
}
