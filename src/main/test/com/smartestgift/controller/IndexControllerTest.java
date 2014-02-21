package com.smartestgift.controller;

import com.smartestgift.common.TestControllerBaseConfiguration;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by dikkini on 21.02.14.
 * Email: dikkini@gmail.com
 */
public class IndexControllerTest extends TestControllerBaseConfiguration {

    @InjectMocks
    private IndexController indexController;

    @Override
    public void setUp() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    public void testIndexPage() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(view().name("index")).andExpect(status().isOk());
    }
}
