package com.smartestgift.common;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by dikkini on 21.02.14.
 * Email: dikkini@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:root-context.xml"})
@WebAppConfiguration
public abstract class TestControllerBaseConfiguration {
    protected MockMvc mockMvc;

    @Before
    public abstract void setUp();
}
