package com.smartestgift.controller;

import com.smartestgift.common.TestControllerBaseConfiguration;
import com.smartestgift.dao.SmartUserDAO;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by dikkini on 21.02.14.
 * Email: dikkini@gmail.com
 */
public class UserProfileControllerTest extends TestControllerBaseConfiguration {

    @InjectMocks
    UserProfileController userProfileController;

    @Autowired
    SmartUserDAO smartUserDAO;

    protected static final String testUsername = "dikkini";

    @Override
    public void setUp() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        userProfileController.smartUserDAO = smartUserDAO;
        this.mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).build();
    }

    @Test
    public void testIndexPage() throws Exception {
        this.mockMvc.perform(get("/user/dikkini")).andExpect(view().name("users/user")).andExpect(status().isOk());
    }
}
