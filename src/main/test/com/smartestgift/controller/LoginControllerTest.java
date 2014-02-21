package com.smartestgift.controller;

import com.smartestgift.common.TestControllerBaseConfiguration;
import com.smartestgift.service.SmartUserService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by dikkini on 21.02.14.
 * Email: dikkini@gmail.com
 */
public class LoginControllerTest extends TestControllerBaseConfiguration {

    @InjectMocks
    private LoginController loginController;

    @Autowired
    SmartUserService smartUserService;

    @Override
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");


        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        loginController.smartUserService = smartUserService;
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testIndexPage() throws Exception {
        this.mockMvc.perform(get("/login")).andExpect(view().name("login"));
    }
}
