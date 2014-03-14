package com.smartestgift.dao;

import com.smartestgift.dao.AuthProviderDAO;
import com.smartestgift.dao.model.AuthProvider;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.utils.ApplicationConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static junit.framework.Assert.assertTrue;

/**
 * Created by dikkini on 20.02.14.
 * Email: dikkini@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations={"classpath:root-context.xml"})
public class AuthProviderDAOTest {

    @Autowired
    AuthProviderDAO authProviderDAO;

    @Test
    public void testAuthProviderDAO() throws Exception {
        AuthProvider facebookProvider = authProviderDAO.find(ApplicationConstants.FACEBOOK_AUTH_PROVIDER_ID);
        assertTrue(facebookProvider.getName().equals("facebook"));
        AuthProvider applicationProvider = authProviderDAO.find(ApplicationConstants.APPLICATION_AUTH_PROVIDER_ID);
        assertTrue(applicationProvider.getName().equals("application"));
        facebookProvider.getSmartUsers();
    }
}
