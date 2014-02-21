package com.smartestgift.controller;

import com.smartestgift.common.TestControllerBaseConfiguration;
import com.smartestgift.dao.AuthProviderDAO;
import com.smartestgift.dao.RoleDAO;
import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.SmartUserDetailsDAO;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.security.UserAuthProvider;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by dikkini on 21.02.14.
 * Email: dikkini@gmail.com
 */
public class SignupControllerTest extends TestControllerBaseConfiguration {

    @InjectMocks
    SignupController signupController;

    @Autowired
    SmartUserDetailsDAO smartUserDetailsDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    AuthProviderDAO authProviderDAO;

    @Autowired
    UserAuthProvider authProvider;

    @Override
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");

        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        signupController.smartUserDetailsDAO = smartUserDetailsDAO;
        signupController.roleDAO = roleDAO;
        signupController.authProviderDAO = authProviderDAO;
        signupController.authProvider = authProvider;
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(signupController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testSignupPage() throws Exception {
        this.mockMvc.perform(get("/signup")).andExpect(view().name("signup")).andExpect(status().isOk());
    }

    @Test
    public void testSignupUser() throws Exception {
        String username = "ahalai_mahalai";
        String email = "op-pa-pa-pa@email.xer";
        String password = "127roseQ";
        String firstName = "Yabeda-beda-beda";
        String lastName = "Gooooooooooodttttttttttterasd";

        this.mockMvc.perform(post("/signup/register")
                .param("username", username)
                .param("email", email)
                .param("password", password)
                .param("firstName", firstName)
                .param("lastName", lastName)
        ).andExpect(view().name("redirect:/profile")).andExpect(status().isFound());

        SmartUserDetails smartUserDetailsByUsername = smartUserDetailsDAO.findSmartUserDetailsByUsername(username);
        SmartUserDetails smartUserDetailsByEmail = smartUserDetailsDAO.findSmartUserDetailsByEmail(email);
        assertTrue(smartUserDetailsByUsername.equals(smartUserDetailsByEmail));
    }
}
